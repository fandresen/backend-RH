package com.fandresena.learn.Exeption;

public class DataConversionException extends RuntimeException {

    public DataConversionException(String message) {
        super(message);
    }

    public DataConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
