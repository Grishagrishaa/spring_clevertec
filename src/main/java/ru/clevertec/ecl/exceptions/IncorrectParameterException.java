package ru.clevertec.ecl.exceptions;

public class IncorrectParameterException extends RuntimeException {
    public IncorrectParameterException() {
    }

    public IncorrectParameterException(String messageCode) {
        super(messageCode);
    }

    public IncorrectParameterException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public IncorrectParameterException(Throwable cause) {
        super(cause);
    }
}