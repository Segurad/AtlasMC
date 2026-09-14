package de.atlasmc.network.io;

public class RequestTimeoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestTimeoutException() {
		super();
	}
	
	public RequestTimeoutException(Throwable cause) {
		super(cause);
	}
	
	public RequestTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RequestTimeoutException(String message) {
		super(message);
	}
	
}
