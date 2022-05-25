package com.example.computerstore.controller.ex;

/**
 * @param
 * @return
 */
public class FileUpdateIOException extends FileUpdateException{
    public FileUpdateIOException() {
    }

    public FileUpdateIOException(String message) {
        super(message);
    }

    public FileUpdateIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUpdateIOException(Throwable cause) {
        super(cause);
    }

    public FileUpdateIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
