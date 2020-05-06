package com.study.app.json.moshi.adapter

import android.util.Log
import com.squareup.moshi.*
import java.io.IOException

class FloatJsonAdapter {

    companion object {

        @JvmStatic
        fun addAdapter(builder: Moshi.Builder): Moshi.Builder {
            return builder.add(Float::class.java, FLOAT_JSON_ADAPTER)
                .add(Float::class.javaObjectType, FLOAT_OBJ_JSON_ADAPTER)
        }

        /**
         * float 默认值
         */
        private const val FLOAT_DEFAULT_VALUE = 0f

        /**
         * float.class
         */
        private val FLOAT_JSON_ADAPTER: JsonAdapter<Float> = object : JsonAdapter<Float>() {
            @FromJson
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Float? {
                reader.nextString()?.apply {
                    try {
                        return@fromJson this.toFloat()
                    } catch (e: Throwable) {
                        Log.w(MoshiCompatJsonAdapterFactory.TAG, e.message ?: "unknown throwable")
                        return@fromJson FLOAT_DEFAULT_VALUE
                    }
                }
                Log.w(MoshiCompatJsonAdapterFactory.TAG, "float value is null")
                return FLOAT_DEFAULT_VALUE
            }

            @ToJson
            @Throws(IOException::class)
            override fun toJson(writer: JsonWriter, value: Float?) {
                writer.value(value)
            }

            override fun toString(): String {
                return "Compat JsonAdapter(float)"
            }
        }

        /**
         * Float 默认值
         */
        private val FLOAT_OBJ_DEFAULT_VALUE: Float? = null

        /**
         * Float.class
         */
        private val FLOAT_OBJ_JSON_ADAPTER: JsonAdapter<Float> = object : JsonAdapter<Float>() {
            @FromJson
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Float? {
                reader.nextString()?.apply {
                    try {
                        return@fromJson this.toFloat()
                    } catch (e: Throwable) {
                        Log.w(MoshiCompatJsonAdapterFactory.TAG, e.message ?: "unknown throwable")
                        return@fromJson FLOAT_OBJ_DEFAULT_VALUE
                    }
                }
                Log.w(MoshiCompatJsonAdapterFactory.TAG, "Float value is null")
                return FLOAT_OBJ_DEFAULT_VALUE
            }

            @ToJson
            @Throws(IOException::class)
            override fun toJson(writer: JsonWriter, value: Float?) {
                writer.value(value)
            }

            override fun toString(): String {
                return "Compat JsonAdapter(Float)"
            }
        }
    }

}