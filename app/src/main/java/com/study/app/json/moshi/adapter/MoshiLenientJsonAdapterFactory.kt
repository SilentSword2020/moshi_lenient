package com.study.app.json.moshi.adapter

import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import org.jetbrains.annotations.NotNull
import java.io.IOException
import java.util.*

/**
 * moshi json格式容忍的工具类
 */
object MoshiLenientJsonAdapterFactory {

    const val TAG = "moshi_json"

    /**
     * 添加json格式容忍的JsonAdapters
     * @param moshi
     */
    @JvmStatic
    fun addLenientJsonAdapters(@NotNull moshi: Moshi): Moshi {
        var builder = moshi.newBuilder()
        builder = IntJsonAdapter.addAdapter(builder)
        builder = LongJsonAdapter.addAdapter(builder)
        builder = FloatJsonAdapter.addAdapter(builder)
        builder = DoubleJsonAdapter.addAdapter(builder)
        builder = ArrayJsonAdapter.addAdapter(builder)
        builder = CollectionJsonAdapter.addAdapter(builder)
        builder = MapJsonAdapter.addAdapter(builder)
        builder = ClassJsonAdapter.addAdapter(builder)
        return builder.build()
    }


    /**
     * json值是否无效
     */
    @JvmStatic
    @Throws(IOException::class)
    fun isJsonValueInvalid(reader: JsonReader): Boolean {
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

}