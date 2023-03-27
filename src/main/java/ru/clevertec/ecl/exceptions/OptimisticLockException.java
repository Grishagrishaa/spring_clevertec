package ru.clevertec.ecl.exceptions;

public class OptimisticLockException extends RuntimeException {
    Object entity;

    public OptimisticLockException() {
    }

    public OptimisticLockException(String message) {
        super(message);
    }

    public OptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public OptimisticLockException(Throwable cause) {
        super(cause);
    }

    public OptimisticLockException(Object entity) {
        this.entity = entity;
    }

    public OptimisticLockException(String message, Throwable cause, Object entity) {
        super(message, cause);
        this.entity = entity;
    }

    public Object getEntity() {
        return this.entity;
    }
}