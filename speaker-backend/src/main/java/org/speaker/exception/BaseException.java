package org.speaker.exception;

/**
 * 业务异常基础类
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    BaseException(String msg) {
        super(msg);
    }
}
