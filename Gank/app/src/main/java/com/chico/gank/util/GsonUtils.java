package com.chico.gank.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Chico
 * @date 2017/3/8
 */

public class GsonUtils {

    /**
     * 从JSON字符串中反序列化T对象
     *
     * @param jsonData
     * @param pClass
     * @param <T>
     * @return
     */
    public static <T> T parseTFromJson(String jsonData, Class<T> pClass) {
        T result;
        try {
            if (!TextUtils.isEmpty(jsonData)) {
                Gson gson = new Gson();
                result = gson.fromJson(jsonData, pClass);
            } else {
                result = null;
            }
        } catch (Exception e) {
            Logger.e("解析异常：" + e.toString());
            result = null;
        }
        return result;
    }

    public static <T> T parseTFromJson(Gson gson, String jsonData, Class<T> pClass) {
        T result;
        try {
            if (!TextUtils.isEmpty(jsonData)) {
                Gson object = gson == null ? new Gson() : gson;
                result = object.fromJson(jsonData, pClass);
            } else {
                result = null;
            }
        } catch (Exception e) {
            Logger.e("解析异常：" + e.toString());
            result = null;
        }
        return result;
    }


    /**
     * 从JSON字符串中反序列化List<T>集合对象
     *
     * @param jsonData JSON字符串
     * @param pClass   T对象的Class
     * @param <T>      对象类型
     * @return List<T>集合对象
     */
    public static <T> List<T> parseListFromJson(String jsonData, Class<T> pClass) {
        List<T> result;
        try {
            if (!TextUtils.isEmpty(jsonData)) {
                Gson gson = new Gson();
                result = gson.fromJson(jsonData, new ListOfJson<>(pClass));
            } else {
                result = null;
            }
        } catch (Exception e) {
            Logger.e("解析异常：" + e.toString());
            result = null;
        }
        return result;
    }

    /**
     * 从JSON字符串中反序列化List<T>集合对象
     *
     * @param gson     Gson对象
     * @param jsonData JSON字符串
     * @param pClass   T对象的Class
     * @param <T>      对象类型
     * @return List<T>  集合对象
     */
    public static <T> List<T> parseListFromJson(Gson gson, String jsonData, Class<T> pClass) {
        List<T> result;
        try {
            if (!TextUtils.isEmpty(jsonData)) {
                Gson object = gson == null ? new Gson() : gson;
                result = object.fromJson(jsonData, new ListOfJson<>(pClass));
            } else {
                result = null;
            }
        } catch (Exception e) {
            Logger.e("解析异常：" + e.toString());
            result = null;
        }
        return result;
    }

    public static <T> T parseTFromJson(String jsonData, Type type) {
        T result;
        try {
            if (!TextUtils.isEmpty(jsonData)) {
                Gson gson = new Gson();
                result = gson.fromJson(jsonData, type);
            } else {
                result = null;
            }
        } catch (Exception e) {
            Logger.e("解析异常：" + e.toString());
            result = null;
        }
        return result;
    }

    /**
     * 序列化T对象为JSON字符串
     *
     * @param t   T对象
     * @param <T> 将要序列化的T对象
     * @return JSON字符串
     */
    public static <T> String parserTToJson(T t) {
        String josnStr;
        try {
            if (t != null) {
                Gson gson = new Gson();
                josnStr = gson.toJson(t);
            } else {
                josnStr = "";
            }
        } catch (Exception e) {
            Logger.e("解析异常：" + e.toString());
            josnStr = "";
        }
        return josnStr;
    }

    /**
     * 序列化List<T>集合对象为JSON字符串
     *
     * @param list List<T>集合对象
     * @param <T>  对象类型
     * @return JSON字符串
     */
    public static <T> String parserListToJson(List<T> list) {
        String josnStr;
        try {
            if (list != null) {
                Gson gson = new Gson();
                josnStr = gson.toJson(list);
            } else {
                josnStr = "";
            }
        } catch (Exception e) {
            Logger.e("解析异常：" + e.toString());
            josnStr = "";
        }
        return josnStr;
    }

    static class ListOfJson<T> implements ParameterizedType {
        private Class<?> mType;

        public ListOfJson(Class<T> type) {
            this.mType = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{mType};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
