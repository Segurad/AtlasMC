package de.atlasmc.chat;

public class ChatFormatException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChatFormatException() {}
	
	public ChatFormatException(String msg) {
		super(msg);
	}
	
	public ChatFormatException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ChatFormatException(Throwable cause) {
		super(cause);
	}

}
