package com.study.app.json.moshi.adapter

import android.util.Log
import com.squareup.moshi.*
import com.study.app.json.moshi.adapter.MoshiLenientJsonAdapterFactory.TAG
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
                reader.nextString()?.apply {
                    try {
                        return@fromJson this.toInt()
                    } catch (e: Throwable) {
                        Log.w(TAG, e.message ?: "unknown throwable")
                        return@fromJson INT_DEFAULT_VALUE
                    }
                }
                Log.w(TAG, "int value is null")
                return INT_DEFAULT_VALUE
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
                reader.nextString()?.apply {
                    try {
                        return@fromJson this.toInt()
                    } catch (e: Throwable) {
                        Log.w(TAG, e.message ?: "unknown throwable")
                        return@fromJson INTEGER_DEFAULT_VALUE
                    }
                }
                Log.w(TAG, "int value is null")
                return INTEGER_DEFAULT_VALUE
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