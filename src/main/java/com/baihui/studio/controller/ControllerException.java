package com.baihui.studio.controller;

/**
 * 控制器层异常
 *
 * @author xiayouxue
 * @date 2014/3/31
 */
public class ControllerException extends RuntimeException {

    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }
}
