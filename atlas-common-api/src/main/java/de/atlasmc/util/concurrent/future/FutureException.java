package de.atlasmc.util.concurrent.future;

public class FutureException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FutureException() {
		super();
	}
	
	public FutureException(String message) {
		super(message);
	}
	
	public FutureException(Throwable cause) {
		super(cause);
	}
	
	public FutureException(String message, Throwable cause) {
		super(message, cause);
	}

}
