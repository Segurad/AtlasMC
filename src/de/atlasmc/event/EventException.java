package de.atlasmc.event;

public class EventException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EventException(String msg) {
		super(msg);
	}
	
	public EventException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
