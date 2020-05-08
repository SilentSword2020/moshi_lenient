package com.study.app.json.moshi.adapter

import com.squareup.moshi.*
import com.study.app.json.moshi.adapter.extensions.fromJsonPrimitiveType
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
                return fromJsonPrimitiveType(reader, FLOAT_DEFAULT_VALUE) {
                    it.toFloat()
                }
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
                return fromJsonPrimitiveType(reader, FLOAT_OBJ_DEFAULT_VALUE) {
                    it.toFloat()
                }
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