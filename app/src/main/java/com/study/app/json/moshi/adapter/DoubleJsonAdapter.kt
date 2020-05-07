package com.study.app.json.moshi.adapter

import android.util.Log
import com.squareup.moshi.*
import com.study.app.json.moshi.adapter.MoshiLenientJsonAdapterFactory.TAG
import java.io.IOException

class DoubleJsonAdapter {

    companion object {

        @JvmStatic
        fun addAdapter(builder: Moshi.Builder): Moshi.Builder {
            return builder.add(Double::class.java, DOUBLE_JSON_ADAPTER)
                .add(Double::class.javaObjectType, DOUBLE_OBJ_JSON_ADAPTER)
        }

        /**
         * double 默认值
         */
        private const val DOUBLE_DEFAULT_VALUE: Double = 0.0

        /**
         * double.class
         */
        private val DOUBLE_JSON_ADAPTER: JsonAdapter<Double> = object : JsonAdapter<Double>() {
            @FromJson
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Double? {
                reader.nextString()?.apply {
                    try {
                        return@fromJson this.toDouble()
                    } catch (e: Throwable) {
                        Log.w(TAG, e.message ?: "unknown throwable")
                        return@fromJson DOUBLE_DEFAULT_VALUE
                    }
                }
                Log.w(TAG, "double value is null")
                return DOUBLE_DEFAULT_VALUE
            }

            @ToJson
            @Throws(IOException::class)
            override fun toJson(writer: JsonWriter, value: Double?) {
                writer.value(value)
            }

            override fun toString(): String {
                return "Compat JsonAdapter(double)"
            }
        }

        /**
         * Double 默认值
         */
        private val DOUBLE_OBJ_DEFAULT_VALUE: Double? = null

        /**
         * Double.class
         */
        private val DOUBLE_OBJ_JSON_ADAPTER: JsonAdapter<Double> = object : JsonAdapter<Double>() {
            @FromJson
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Double? {
                reader.nextString()?.apply {
                    try {
                        return@fromJson this.toDouble()
                    } catch (e: Throwable) {
                        Log.w(TAG, e.message ?: "unknown throwable")
                        return@fromJson DOUBLE_OBJ_DEFAULT_VALUE
                    }
                }
                Log.w(TAG, "Double value is null")
                return DOUBLE_OBJ_DEFAULT_VALUE
            }

            @ToJson
            @Throws(IOException::class)
            override fun toJson(writer: JsonWriter, value: Double?) {
                writer.value(value)
            }

            override fun toString(): String {
                return "Compat JsonAdapter(Double)"
            }
        }
    }

}