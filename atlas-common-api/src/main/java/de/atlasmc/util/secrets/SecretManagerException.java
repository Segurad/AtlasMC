package de.atlasmc.util.secrets;

public class SecretManagerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SecretManagerException(String message) {
		super(message);
	}
	
	public SecretManagerException(Throwable cause) {
		super(cause);
	}
	
	public SecretManagerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SecretManagerException() {
		super();
	}

}
