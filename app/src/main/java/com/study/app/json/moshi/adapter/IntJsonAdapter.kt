package com.study.app.json.moshi.adapter

import com.squareup.moshi.*
import java.io.IOException

class IntJsonAdapter {

    companion object {

        @JvmStatic
        fun addAdapter(builder: Moshi.Builder): Moshi.Builder {
            return builder.add(Int::class.java, INT_JSON_ADAPTER)
                .add(Int::class.javaObjectType, INTEGER_JSON_ADAPTER)
        }

        /**
         * int 默认值
         */
        private const val INT_DEFAULT_VALUE = 0

        /**
         * int.class
         */
        private val INT_JSON_ADAPTER: JsonAdapter<Int> = object : JsonAdapter<Int>() {
            @FromJson
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Int? {
                return fromJsonPrimitiveType(reader, INT_DEFAULT_VALUE) {
                    it.toInt()
                }
            }

            @ToJson
            @Throws(IOException::class)
            override fun toJson(writer: JsonWriter, value: Int?) {
                writer.value(value)
            }

            override fun toString(): String {
                return "Compat JsonAdapter(int)"
            }
        }

        /**
         * Integer 默认值
         */
        private val INTEGER_DEFAULT_VALUE: Int? = null

        /**
         * Integer.class
         */
        private val INTEGER_JSON_ADAPTER: JsonAdapter<Int> = object : JsonAdapter<Int>() {
            @FromJson
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Int? {
                return fromJsonPrimitiveType(reader, INTEGER_DEFAULT_VALUE) {
                    it.toInt()
                }
            }

            @ToJson
            @Throws(IOException::class)
            override fun toJson(writer: JsonWriter, value: Int?) {
                writer.value(value)
            }

            override fun toString(): String {
                return "Compat JsonAdapter(Integer)"
            }
        }
    }

}