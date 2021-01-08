package com.chico.gank.http.exception;

/**
 * @ClassName: ResponseException
 * @Description:
 * @Author: Chico
 * @Date: 2019/8/26 15:48
 */
public class EmptyException extends CustomException {

    static final long serialVersionUID = -7034897190745766939L;

    public EmptyException() {
        super(0x000001);
    }

    public EmptyException(String message) {
        super(0x000001, message);
    }

    public EmptyException(String message, Throwable cause) {
        super(0x000001, message, cause);
    }
}
