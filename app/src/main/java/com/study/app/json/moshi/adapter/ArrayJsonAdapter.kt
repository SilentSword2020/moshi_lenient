package com.study.app.json.moshi.adapter

import com.squareup.moshi.*
import com.squareup.moshi.JsonAdapter.Factory
import java.io.IOException
import java.lang.reflect.Array
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type
import java.util.*

/**
 * [] <- "", 此时："" 视为 []
 *
 * Converts arrays to JSON arrays containing their converted contents. This
 * supports both primitive and object arrays.
 *
 * 参考Moshi源码中的ArrayJsonAdapter
 */
class ArrayJsonAdapter(
    private val elementClass: Class<*>,
    private val elementAdapter: JsonAdapter<Any>
) : JsonAdapter<Any>() {
    @FromJson
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Any {
        //使用一个reader的拷贝，来提前获取数据进行检查
        val cloneReader = reader.peekJson()
        val nextValueEmpty = cloneReader.nextString().isNullOrEmpty()
        cloneReader.close()
        if (nextValueEmpty) {
            //如果json值为空串，跳过这个值，不处理
            reader.skipValue()
            //返回一个空数组
            return Array.newInstance(elementClass, 0)
        }
        val list: MutableList<Any?> = ArrayList()
        reader.beginArray()
        while (reader.hasNext()) {
            list.add(elementAdapter.fromJson(reader))
        }
        reader.endArray()
        val array = Array.newInstance(elementClass, list.size)
        for (i in list.indices) {
            Array.set(array, i, list[i])
        }
        return array
    }

    @ToJson
    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: Any?) {
        writer.beginArray()
        var i = 0
        val size = Array.getLength(value)
        while (i < size) {
            elementAdapter.toJson(writer, Array.get(value, i))
            i++
        }
        writer.endArray()
    }

    override fun toString(): String {
        return "$elementAdapter.array()"
    }

    companion object {
        private val FACTORY = Factory { type, annotations, moshi ->
            val elementType = arrayComponentType(type) ?: return@Factory null
            if (annotations.isNotEmpty()) return@Factory null
            val elementClass = Types.getRawType(elementType)
            val elementAdapter = moshi.adapter<Any>(elementType)
            ArrayJsonAdapter(elementClass, elementAdapter).nullSafe()
        }

        @JvmStatic
        fun addAdapter(builder: Moshi.Builder): Moshi.Builder {
            return builder.add(ArrayJsonAdapter.FACTORY)
        }

        /**
         * Returns the element type of `type` if it is an array type, or null if it is not an
         * array type.
         */
        private fun arrayComponentType(type: Type?): Type? {
            return when (type) {
                is GenericArrayType -> {
                    type.genericComponentType
                }
                is Class<*> -> {
                    type.componentType
                }
                else -> {
                    null
                }
            }
        }
    }


}