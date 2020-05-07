package com.study.app.json.moshi.adapter.reflect;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 反射工具类
 */
public class ReflectUtil {
    private static final String TAG = "reflect";

    /**
     * 获取对应的构造函数
     *
     * @param clazz
     * @param parameterTypes
     * @return
     */
    public static Constructor getConstructor(Class clazz, Class<?>... parameterTypes) {
        if (clazz == null) {
            return null;
        }
        try {
            Constructor constructor = clazz.getDeclaredConstructor(parameterTypes);
            if (constructor != null) {
                constructor.setAccessible(true);
            }
            return constructor;
        } catch (NoSuchMethodException e) {
            Log.e(TAG, e.getLocalizedMessage());
        } catch (Throwable e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获取对应的method
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Class clazz, String methodName, Class<?>... parameterTypes) {
        if (clazz == null || TextUtils.isEmpty(methodName)) {
            return null;
        }
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            if (method != null) {
                method.setAccessible(true);
            }
            return method;
        } catch (NoSuchMethodException e) {
            Log.e(TAG, e.getLocalizedMessage());
        } catch (Throwable e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return null;
    }


}
