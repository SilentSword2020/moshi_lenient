package com.study.app.json.moshi.adapter

import com.squareup.moshi.Moshi

object MoshiCompatJsonAdapterFactory {

    const val TAG = "moshi_json"


    fun addCompatJsonAdapters(moshi: Moshi): Moshi {
        var builder = moshi.newBuilder()
        builder = IntJsonAdapter.addAdapter(builder)
        builder = LongJsonAdapter.addAdapter(builder)
        builder = FloatJsonAdapter.addAdapter(builder)
        builder = DoubleJsonAdapter.addAdapter(builder)
        builder = ArrayJsonAdapter.addAdapter(builder)
        builder = CollectionJsonAdapter.addAdapter(builder)
        return builder.build()
    }


}