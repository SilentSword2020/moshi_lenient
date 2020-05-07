package com.study.app.json.moshi.adapter

import com.squareup.moshi.*
import com.squareup.moshi.JsonAdapter.Factory
import java.io.IOException
import java.lang.reflect.Array
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

/**
 * [] <- "", 此时："" 视为 []
 *
 * Converts collection types to JSON arrays containing their converted contents.
 */
abstract class CollectionJsonAdapter<C : MutableCollection<T?>?, T> private constructor(
    private val elementAdapter: JsonAdapter<T>
) : JsonAdapter<C>() {
    abstract fun newCollection(): C

    @FromJson
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): C {
        //使用一个reader的拷贝，来提前获取数据进行检查
        val cloneReader = reader.peekJson()
        val nextValueEmpty = cloneReader.nextString().isNullOrEmpty()
        cloneReader.close()
        if (nextValueEmpty) {
            //如果json值为空串，跳过这个值，不处理
            reader.skipValue()
            //返回一个空列表
            return newCollection()
        }
        val result = newCollection()
        reader.beginArray()
        while (reader.hasNext()) {
            result?.add(elementAdapter.fromJson(reader))
        }
        reader.endArray()
        return result
    }

    @ToJson
    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: C?) {
        writer.beginArray()
        for (element in value!!) {
            elementAdapter.toJson(writer, element)
        }
        writer.endArray()
    }

    override fun toString(): String {
        return "$elementAdapter.collection()"
    }

    companion object {
        @JvmStatic
        fun addAdapter(builder: Moshi.Builder): Moshi.Builder {
            return builder.add(CollectionJsonAdapter.FACTORY)
        }

        private val FACTORY = Factory { type, annotations, moshi ->
            val rawType = Types.getRawType(type)
            if (annotations.isNotEmpty()) return@Factory null
            if (rawType == List::class.java || rawType == Collection::class.java) {
                return@Factory newArrayListAdapter<Any>(type, moshi).nullSafe()
            } else if (rawType == Set::class.java) {
                return@Factory newLinkedHashSetAdapter<Any>(type, moshi).nullSafe()
            }
            null
        }

        private fun <T> newArrayListAdapter(
            type: Type?,
            moshi: Moshi
        ): CollectionJsonAdapter<MutableList<T?>, T> {
            val elementType = Types.collectionElementType(type, MutableCollection::class.java)
            val elementAdapter: JsonAdapter<T> = moshi.adapter(elementType)
            return object : CollectionJsonAdapter<MutableList<T?>, T>(elementAdapter) {
                override fun newCollection(): MutableList<T?> {
                    return ArrayList()
                }
            }
        }

        private fun <T> newLinkedHashSetAdapter(
            type: Type?,
            moshi: Moshi
        ): JsonAdapter<MutableSet<T?>> {
            val elementType = Types.collectionElementType(type, MutableCollection::class.java)
            val elementAdapter: JsonAdapter<T> = moshi.adapter(elementType)
            return object : CollectionJsonAdapter<MutableSet<T?>, T>(elementAdapter) {
                override fun newCollection(): MutableSet<T?> {
                    return LinkedHashSet()
                }
            }
        }
    }

}