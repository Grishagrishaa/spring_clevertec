package ru.clevertec.ecl.exceptions;

public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(Exception cause) {
		super(cause);
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(String message, Exception cause) {
		super(message, cause);
	}

}