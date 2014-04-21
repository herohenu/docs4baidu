package com.baihui.studio.service;

/**
 * 服务层异常
 *
 * @author xiayouxue
 * @date 2014/3/31
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
