package com.study.app.json.moshi.adapter

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.study.app.json.moshi.adapter.MoshiLenientJsonAdapterFactory.TAG
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


}

/**
 * json值是否无效
 */
@Throws(IOException::class)
inline fun <T> JsonAdapter<T>.isJsonValueInvalid(reader: JsonReader): Boolean {
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

/**
 * JsonAdapter扩展方法: 用于反序列化基本类型
 */
inline fun <reified T> JsonAdapter<T>.fromJsonPrimitiveType(
    reader: JsonReader,
    defaultValue: T?,
    crossinline transform: (String) -> T
): T? {
    reader.nextString()?.apply {
        try {
            return@fromJsonPrimitiveType transform(this)
        } catch (e: Throwable) {
            Log.w(TAG, e.message ?: "unknown throwable")
            return@fromJsonPrimitiveType defaultValue
        }
    }
    Log.w(TAG, "${T::class.java} value is null")
    return defaultValue
}