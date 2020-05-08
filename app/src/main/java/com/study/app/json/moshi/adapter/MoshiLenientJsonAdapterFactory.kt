package com.study.app.json.moshi.adapter

import com.squareup.moshi.Moshi
import org.jetbrains.annotations.NotNull

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
}
