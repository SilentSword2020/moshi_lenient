package com.study.app.json.moshi.adapter.common;

import com.squareup.moshi.Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;

import static com.squareup.moshi.internal.Util.getGenericSupertype;
import static com.squareup.moshi.internal.Util.resolve;

/**
 * Type的工具类
 */
public final class TypesExt {
    private TypesExt() {
    }

    /**
     * Returns a two element array containing this map's key and value types in positions 0 and 1
     * respectively.
     */
    public static Type[] mapKeyAndValueTypes(Type context, Class<?> contextRawType) {
        // Work around a problem with the declaration of java.util.Properties. That class should extend
        // Hashtable<String, String>, but it's declared to extend Hashtable<Object, Object>.
        if (context == Properties.class) return new Type[]{String.class, String.class};

        Type mapType = getSupertype(context, contextRawType, Map.class);
        if (mapType instanceof ParameterizedType) {
            ParameterizedType mapParameterizedType = (ParameterizedType) mapType;
            return mapParameterizedType.getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    /**
     * Returns the generic form of {@code supertype}. For example, if this is {@code
     * ArrayList<String>}, this returns {@code Iterable<String>} given the input {@code
     * Iterable.class}.
     *
     * @param supertype a superclass of, or interface implemented by, this.
     */
    public static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
        if (!supertype.isAssignableFrom(contextRawType)) throw new IllegalArgumentException();
        return resolve(context, contextRawType,
                getGenericSupertype(context, contextRawType, supertype));
    }

    public static Type getGenericSuperclass(Type type) {
        Class<?> rawType = Types.getRawType(type);
        return resolve(type, rawType, rawType.getGenericSuperclass());
    }
}
