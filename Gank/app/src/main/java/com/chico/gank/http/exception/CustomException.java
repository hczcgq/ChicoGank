package com.chico.gank.http.exception;

import android.os.Build;

import androidx.annotation.RequiresApi;


public class CustomException extends Exception{
    static final long serialVersionUID = -7034897190745766939L;
    private int code;
    private Object data;

    public CustomException() {
        super();
    }
    public CustomException(int code) {
        super();
        this.code = code;
    }
    public CustomException(int code, String message, Object data){
        this(message);
        this.code = code;
        this.data = data;
    }

    public CustomException(int code, String message){
        this(message);
    this.code = code;
    }
    private CustomException(String message) {
        super(message);
    }

    public CustomException(int code ,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


    private CustomException(Throwable cause) {
        super(cause);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private CustomException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
