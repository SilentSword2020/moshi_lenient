@file:JvmName("JsonAdapterExt")

package com.study.app.json.moshi.adapter.extensions

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.study.app.json.moshi.adapter.MoshiLenientJsonAdapterFactory
import java.io.IOException
import java.util.*


/**
 * json值是否无效
 */
@Throws(IOException::class)
inline fun <T> JsonAdapter<T>.isJsonValueInvalid(reader: JsonReader): Boolean {
    //使用一个reader的拷贝，来提前获取数据进行检查
    val cloneReader = reader.peekJson()
    var invalid = when (val jsonValue = cloneReader.readJsonValue()) {
        null -> true
        is CharSequence -> jsonValue.length == 0
        is ArrayList<*> -> jsonValue.isEmpty()
        else -> false
    }
    cloneReader.close()
    return invalid
}

/**
 * JsonAdapter扩展方法: 用于反序列化基本类型
 */
@Throws(JsonDataException::class)
inline fun <reified T> JsonAdapter<T>.fromJsonPrimitiveType(
    reader: JsonReader,
    defaultValue: T?,
    crossinline transform: (String) -> T
): T? {
    reader.nextString()?.apply {
        try {
            if (isEmpty()) {
                return defaultValue
            }
            return@fromJsonPrimitiveType transform(this)
        } catch (e: NumberFormatException) {
            //父类.class.isAssignableFrom(子类.class)
            if (Int::class.java.isAssignableFrom(T::class.java)
                || Int::class.javaObjectType.isAssignableFrom(T::class.java)
            ) {
                return@fromJsonPrimitiveType parseInt(reader, this) as T?
            } else if (Long::class.java.isAssignableFrom(T::class.java)
                || Long::class.javaObjectType.isAssignableFrom(T::class.java)
            ) {
                return@fromJsonPrimitiveType parseLong(reader, this) as T?
            } else {
                //其他数字格式异常：直接抛出JsonDataException
                throw JsonDataException(
                    "Expected an ${T::class.java.simpleName} but was " + this
                            + " at path " + reader.path
                )
            }
        } catch (e: Throwable) {
            Log.w(MoshiLenientJsonAdapterFactory.TAG, e.message ?: "unknown throwable")
            return@fromJsonPrimitiveType defaultValue
        }
    }
    Log.w(MoshiLenientJsonAdapterFactory.TAG, "${T::class.java} value is null")
    return defaultValue
}


/**
 * 解析int
 */
@Throws(JsonDataException::class)
fun <T> JsonAdapter<T>.parseInt(reader: JsonReader, value: String): Int? {
    val asDouble: Double = try {
        value.toDouble()
    } catch (e: NumberFormatException) {
        throw JsonDataException(
            "Expected an int but was " + value
                    + " at path " + reader.path
        )
    }
    val result = asDouble.toInt()
    if (result.toDouble() != asDouble) { // Make sure no precision was lost casting to 'int'.
        throw JsonDataException(
            "Expected an int but was " + value
                    + " at path " + reader.path
        )
    }
    return result
}

/**
 * 解析long
 */
@Throws(JsonDataException::class)
fun <T> JsonAdapter<T>.parseLong(reader: JsonReader, value: String): Long? {
    val asDouble: Double = try {
        value.toDouble()
    } catch (e: NumberFormatException) {
        throw JsonDataException(
            "Expected an long but was " + value
                    + " at path " + reader.path
        )
    }
    val result = asDouble.toLong()
    if (result.toDouble() != asDouble) { // Make sure no precision was lost casting to 'int'.
        throw JsonDataException(
            "Expected an long but was " + value
                    + " at path " + reader.path
        )
    }
    return result
}
