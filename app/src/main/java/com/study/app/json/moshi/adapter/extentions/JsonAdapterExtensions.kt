@file:JvmName("JsonAdapterUtil")

package com.study.app.json.moshi.adapter.extentions

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * 通过分析JsonDataException的message格式进行分别处理
 *
 * 解析由 PHP 构造的 JSON 结构数据，容忍目前常见的不规范的格式。
 * 允许：{} <- [] / ""，[] <- "", Integer <- "", Float <- ""
 */
fun <T> JsonAdapter<T>.lenientPhp(): JsonAdapter<T> {
    val delegate = this
    return object : JsonAdapter<T>() {
        @Throws(IOException::class)
        override fun fromJson(reader: JsonReader): T? {
            val jsonReader = reader.peekJson()
            return try {
                // 使用分身reader解析，不消费传入的reader
                delegate.fromJson(jsonReader).also {
                    // 消费传入的reader
                    reader.skipValue()
                }
            } catch (e: JsonDataException) {
                val matcher = isValueTypeNotMatched(e.message)
                if (matcher != null) {
                    // 改用传入的reader解析
                    val jsonValue = reader.readJsonValue() ?: throw e
                    try {
                        tryLenientForPhpJson(delegate, jsonValue, matcher, e)
                    } catch (e2: JsonDataException) {
                        throw e
                    } catch (e2: Exception) {
                        Log.w("moshi_json", e)
                        throw e
                    }
                } else {
                    throw e
                }
            }
        }

        @Throws(IOException::class)
        override fun toJson(writer: JsonWriter, value: T?) {
            delegate.toJson(writer, value)
        }

        override fun toString(): String {
            return "$delegate.lenientPhp()"
        }
    }
}

private fun JsonReader.skipAll() {
    while (true) {
        when (peek()) {
            JsonReader.Token.END_DOCUMENT -> {
                return
            }
            JsonReader.Token.END_ARRAY -> {
                endArray()
            }
            JsonReader.Token.END_OBJECT -> {
                endObject()
            }
            else -> {
                skipValue()
            }
        }
    }
}

private val EXPECTED_PATTERN: ThreadLocal<Pattern> = object : ThreadLocal<Pattern>() {
    override fun initialValue(): Pattern? {
        return Pattern.compile("Expected ([\\w ]+) but was (.*) at path [$](.+)")
    }
}

private fun isValueTypeNotMatched(message: String?): Matcher? {
    if (message == null) return null
    if (!message.startsWith("Expected ")) return null
    val pattern = EXPECTED_PATTERN.get() ?: return null
    val matcher = pattern.matcher(message)
    if (!matcher.find()) return null
    return matcher
}

private fun <T> tryLenientForPhpJson(
    delegate: JsonAdapter<T>,
    jsonValue: Any,
    matcher: Matcher,
    rawE: JsonDataException
): T? {
    var expected = matcher.group(1)!!
    var actual = matcher.group(2)!!
    var path = matcher.group(3)!!
    var count = 0
    var prevPath = path

    replaceAndRetry@ do {
        if (count > 0) {
            try {
                return delegate.fromJsonValue(jsonValue)
            } catch (e: JsonDataException) {
                val matcher2 = isValueTypeNotMatched(e.message)
                if (matcher2 != null) {
                    expected = matcher2.group(1)!!
                    actual = matcher2.group(2)!!
                    path = matcher2.group(3)!!
                    if (path == prevPath) {
                        break@replaceAndRetry
                    }
                    prevPath = path
                } else {
                    break@replaceAndRetry
                }
            }
        }
        when (expected) {
            "an int", "a long", "a double", "NUMBER" -> if ("" == actual || ", a java.lang.String," == actual) {
                val accessor = accessorOfPath(jsonValue, path) ?: break@replaceAndRetry
                val (value, setter) = accessor
                if ("" == value) {
                    // "" 视为 null
                    setter(null)
                    continue@replaceAndRetry
                }
            }
            "BEGIN_ARRAY" -> if ("STRING" == actual || ", a java.lang.String," == actual) {
                val accessor = accessorOfPath(jsonValue, path) ?: break@replaceAndRetry
                val (value, setter) = accessor
                if ("" == value) {
                    // "" 视为 []
                    setter(mutableListOf<Any>())
                    continue@replaceAndRetry
                }
            }
            "BEGIN_OBJECT" -> if ("STRING" == actual || ", a java.lang.String," == actual) {
                val accessor = accessorOfPath(jsonValue, path) ?: break@replaceAndRetry
                val (value, setter) = accessor
                if ("" == value) {
                    // "" 视为 null
                    setter(null)
                    continue@replaceAndRetry
                }
            } else if ("BEGIN_ARRAY" == actual || "[], a java.util.ArrayList," == actual) {
                val accessor = accessorOfPath(jsonValue, path) ?: break@replaceAndRetry
                val (value, setter) = accessor
                if (emptyList<Any>() == value) {
                    // [] 视为 null
                    setter(null)
                    continue@replaceAndRetry
                }
            }
        }
        break@replaceAndRetry
    } while (++count < 500)
    throw rawE
}

typealias Setter = (Any?) -> Unit

@Suppress("UNCHECKED_CAST")
private fun accessorOfPath(jsonValue: Any?, path: String, offset: Int = 0): Pair<Any?, Setter>? {
    if (path.startsWith(".", offset)) {
        val nameEndPos = path.indexOfAny(charArrayOf('.', '['), offset + 1)
        val map = jsonValue as? MutableMap<String, Any?> ?: return null
        return if (nameEndPos < 0) {
            // got it
            val name = path.substring(offset + 1)
            map[name] to { v -> map[name] = v }
        } else {
            val name = path.substring(offset + 1, nameEndPos)
            accessorOfPath(map[name], path, nameEndPos)
        }
    }
    if (path.startsWith("[", offset)) {
        // [1]
        val indexEndPos = path.indexOf(']', offset + 1)
        if (indexEndPos < 0) return null
        val list = jsonValue as? MutableList<Any?> ?: return null
        val index = try {
            Integer.parseInt(path.substring(offset + 1, indexEndPos))
        } catch (e: NumberFormatException) {
            return null
        }
        if (index >= list.size) return null
        return if (indexEndPos >= path.length - 1) {
            // got it
            list[index] to { v -> list[index] = v }
        } else {
            accessorOfPath(list[index], path, indexEndPos + 1)
        }
    }
    return null
}
