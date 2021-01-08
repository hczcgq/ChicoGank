package com.chico.gank.http.exception;

import androidx.annotation.NonNull;

import java.util.HashMap;

/**
 * @ClassName: ResponseException
 * @Description:
 * @Author: Chico
 * @Date: 2019/8/26 15:48
 */
public class ResponseException extends Exception {

    public final int code;
    private static final HashMap<Integer, String> messages = new HashMap<>();

    public ResponseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseException(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private final String message;

    private Object data = null;

    public boolean isDeletedOnForum() {
        return code == 108;
    }

    @Override
    @NonNull
    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
