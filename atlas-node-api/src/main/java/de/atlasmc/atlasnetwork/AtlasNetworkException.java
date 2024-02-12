package de.atlasmc.atlasnetwork;

public class AtlasNetworkException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AtlasNetworkException(String message) {
		super(message);
	}
	
	public AtlasNetworkException(String message, Throwable cause) {
		super(message, cause);
	}

}
