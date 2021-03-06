/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.study.app.json.moshi.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import com.squareup.moshi.Types;
import com.study.app.json.moshi.adapter.common.TypesExt;
import com.study.app.json.moshi.adapter.reflect.ReflectUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.study.app.json.moshi.adapter.MoshiLenientJsonAdapterFactory.TAG;
import static com.study.app.json.moshi.adapter.extensions.JsonAdapterExt.isJsonValueInvalid;

/**
 * {} <- "", 此时："" 视为 null
 * <p>
 * Converts maps with string keys to JSON objects.
 * <p>
 * TODO: support maps with other key types and convert to/from strings.
 * <p>
 * 参考Moshi内置的MapJsonAdapter
 */
final class MapJsonAdapter<K, V> extends JsonAdapter<Map<K, V>> {
    public static final Factory FACTORY = new Factory() {
        @Override
        public @Nullable
        JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations, Moshi moshi) {
            if (!annotations.isEmpty()) return null;
            Class<?> rawType = Types.getRawType(type);
            if (rawType != Map.class) return null;
            Type[] keyAndValue = TypesExt.mapKeyAndValueTypes(type, rawType);
            return new MapJsonAdapter<>(moshi, keyAndValue[0], keyAndValue[1]).nullSafe();
        }
    };

    private final JsonAdapter<K> keyAdapter;
    private final JsonAdapter<V> valueAdapter;

    private final ConcurrentHashMap<String, Method> methodCache = new ConcurrentHashMap<>(3);
    private volatile Constructor mapConstructor;
    private static final String METHOD_PROMOTE_VALUE_TO_NAME = "promoteValueToName";
    private static final String METHOD_PROMOTE_NAME_TO_VALUE = "promoteNameToValue";
    private static final String CLASS_LINKED_HASH_TREE_MAP = "com.squareup.moshi.LinkedHashTreeMap";

    MapJsonAdapter(Moshi moshi, Type keyType, Type valueType) {
        this.keyAdapter = moshi.adapter(keyType);
        this.valueAdapter = moshi.adapter(valueType);
    }

    @ToJson
    @Override
    public void toJson(JsonWriter writer, Map<K, V> map) throws IOException {
        writer.beginObject();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getKey() == null) {
                throw new JsonDataException("Map key is null at " + writer.getPath());
            }
            promoteValueToName(writer);
            keyAdapter.toJson(writer, entry.getKey());
            valueAdapter.toJson(writer, entry.getValue());
        }
        writer.endObject();
    }

    /**
     * 反射调用方法：JsonWriter.promoteValueToName()
     *
     * @param writer
     */
    private void promoteValueToName(@NonNull JsonWriter writer) {
        Method method = methodCache.get(METHOD_PROMOTE_VALUE_TO_NAME);
        if (method != null) {
            try {
                method.invoke(writer, (Object[]) null);
            } catch (IllegalAccessException e) {
                Log.w(TAG, e.getMessage());
            } catch (InvocationTargetException e) {
                Log.w(TAG, e.getMessage());
            }
        } else {
            method = ReflectUtil.getMethod(JsonWriter.class, METHOD_PROMOTE_VALUE_TO_NAME, (Class<?>[]) null);
            if (method != null) {
                methodCache.putIfAbsent(METHOD_PROMOTE_VALUE_TO_NAME, method);
                promoteValueToName(writer);
            }
        }
    }

    @FromJson
    @Override
    public Map<K, V> fromJson(JsonReader reader) throws IOException {
        if (isJsonValueInvalid(this, reader)) {
            //如果json值为无效，跳过这个值，不处理
            reader.skipValue();
            //返回null
            return null;
        }
        Map<K, V> result = createMap();
        reader.beginObject();
        while (reader.hasNext()) {
            promoteNameToValue(reader);
            K name = keyAdapter.fromJson(reader);
            V value = valueAdapter.fromJson(reader);
            V replaced = result.put(name, value);
            if (replaced != null) {
                throw new JsonDataException("Map key '" + name + "' has multiple values at path "
                        + reader.getPath() + ": " + replaced + " and " + value);
            }
        }
        reader.endObject();
        return result;
    }

    /**
     * 创建map
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<K, V> createMap() {
        try {
            if (mapConstructor == null) {
                synchronized (this) {
                    if (mapConstructor == null) {
                        mapConstructor = ReflectUtil.getConstructor(Class.forName(CLASS_LINKED_HASH_TREE_MAP), (Class<?>[]) null);
                    }
                }
            }
            return (Map<K, V>) mapConstructor.newInstance((Object[]) null);
        } catch (ClassNotFoundException e) {
            Log.w(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            Log.w(TAG, e.getMessage());
        } catch (InstantiationException e) {
            Log.w(TAG, e.getMessage());
        } catch (InvocationTargetException e) {
            Log.w(TAG, e.getMessage());
        }
        return new LinkedHashMap<>();
    }

    /**
     * 反射调用方法：JsonReader.promoteNameToValue()
     *
     * @param reader
     */
    private void promoteNameToValue(@NonNull JsonReader reader) {
        Method method = methodCache.get(METHOD_PROMOTE_NAME_TO_VALUE);
        if (method != null) {
            try {
                method.invoke(reader, (Object[]) null);
            } catch (IllegalAccessException e) {
                Log.w(TAG, e.getMessage());
            } catch (InvocationTargetException e) {
                Log.w(TAG, e.getMessage());
            }
        } else {
            method = ReflectUtil.getMethod(JsonWriter.class, METHOD_PROMOTE_NAME_TO_VALUE, (Class<?>[]) null);
            if (method != null) {
                methodCache.putIfAbsent(METHOD_PROMOTE_NAME_TO_VALUE, method);
                promoteNameToValue(reader);
            }
        }
    }

    @Override
    public String toString() {
        return "JsonAdapter(" + keyAdapter + "=" + valueAdapter + ")";
    }


    @NonNull
    public static Moshi.Builder addAdapter(@NotNull Moshi.Builder builder) {
        return builder.add(MapJsonAdapter.FACTORY);
    }
}
