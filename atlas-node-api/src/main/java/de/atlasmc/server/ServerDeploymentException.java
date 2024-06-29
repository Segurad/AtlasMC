package de.atlasmc.server;

public class ServerDeploymentException extends ServerException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerDeploymentException(String msg) {
		super(msg);
	}
	
	public ServerDeploymentException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
