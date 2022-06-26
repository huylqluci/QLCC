package com.nhom43.quanlychungcubackendgradle.ex;

// Lỗi máy chủ nội bộ -- "error": "Internal Server Error"
public class    SpringException extends RuntimeException {

    public SpringException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringException(String exMessage) {
        super(exMessage);
    }

}
