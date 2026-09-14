package de.atlasmc.network.io;

public class RequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestException() {
		super();
	}
	
	public RequestException(Throwable cause) {
		super(cause);
	}
	
	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RequestException(String message) {
		super(message);
	}
	
}
