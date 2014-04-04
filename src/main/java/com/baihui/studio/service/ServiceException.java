package com.baihui.studio.service;

/**
 * 服务层异常
 *
 * @author xiayouxue
 * @date 2014/3/31 20:29
 * @modify 新增XX功能 xiayouxue 2014/3/31 20:29
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
