package de.atlasmc.node.server;

public class ServerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ServerException(String msg) {
		super(msg);
	}
	
	public ServerException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
