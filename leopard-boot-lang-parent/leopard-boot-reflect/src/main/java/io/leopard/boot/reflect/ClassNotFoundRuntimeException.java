package io.leopard.boot.reflect;

public class ClassNotFoundRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClassNotFoundRuntimeException(String message) {
		super(message);
	}

	public ClassNotFoundRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
