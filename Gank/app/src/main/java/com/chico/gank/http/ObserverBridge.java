package com.chico.gank.http;

import com.chico.gank.util.GsonUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.observers.DisposableObserver;


/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
public abstract class ObserverBridge<T> extends DisposableObserver<T> {

    private String[] s = new String[]{"", "0", "false", "{}", "[]"};

    protected T nullInstance;

    public T getNullInstance() {
        if (nullInstance == null) {
            initNullInstance();
        }
        return nullInstance;
    }

    public void initNullInstance() {
        Type type = getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            return;
        }
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        if (types.length == 0) {
            return;
        }
        for (String s1 : s) {
            if (nullInstance == null) {
                nullInstance = GsonUtils.parseTFromJson(s1, types[0]);
                if (nullInstance != null) {
                    return;
                }
            }
        }
    }
}
