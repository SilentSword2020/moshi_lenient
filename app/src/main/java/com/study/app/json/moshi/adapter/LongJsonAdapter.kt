package com.study.app.json.moshi.adapter

import android.util.Log
import com.squareup.moshi.*
import java.io.IOException

class LongJsonAdapter {

    companion object {

        @JvmStatic
        fun addAdapter(builder: Moshi.Builder): Moshi.Builder {
            return builder.add(Long::class.java, LONG_JSON_ADAPTER)
                .add(Long::class.javaObjectType, LONG_OBJ_JSON_ADAPTER)
        }

        /**
         * long 默认值
         */
        private const val LONG_DEFAULT_VALUE = 0L

        /**
         * long.class
         */
        private val LONG_JSON_ADAPTER: JsonAdapter<Long> = object : JsonAdapter<Long>() {
            @FromJson
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Long? {
                reader.nextString()?.apply {
                    try {
                        return@fromJson this.toLong()
                    } catch (e: Throwable) {
                        Log.w(MoshiCompatJsonAdapterFactory.TAG, e.message ?: "unknown throwable")
                        return@fromJson LONG_DEFAULT_VALUE
                    }
                }
                Log.w(MoshiCompatJsonAdapterFactory.TAG, "long value is null")
                return LONG_DEFAULT_VALUE
            }

            @ToJson
            @Throws(IOException::class)
            override fun toJson(writer: JsonWriter, value: Long?) {
                writer.value(value)
            }

            override fun toString(): String {
                return "Compat JsonAdapter(long)"
            }
        }

        /**
         * Long 默认值
         */
        private val LONG_OBJ_DEFAULT_VALUE: Long? = null

        /**
         * Long.class
         */
        private val LONG_OBJ_JSON_ADAPTER: JsonAdapter<Long> = object : JsonAdapter<Long>() {
            @FromJson
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Long? {
                reader.nextString()?.apply {
                    try {
                        return@fromJson this.toLong()
                    } catch (e: Throwable) {
                        Log.w(MoshiCompatJsonAdapterFactory.TAG, e.message ?: "unknown throwable")
                        return@fromJson LONG_OBJ_DEFAULT_VALUE
                    }
                }
                Log.w(MoshiCompatJsonAdapterFactory.TAG, "Long value is null")
                return LONG_OBJ_DEFAULT_VALUE
            }

            @ToJson
            @Throws(IOException::class)
            override fun toJson(writer: JsonWriter, value: Long?) {
                writer.value(value)
            }

            override fun toString(): String {
                return "Compat JsonAdapter(Long)"
            }
        }
    }

}