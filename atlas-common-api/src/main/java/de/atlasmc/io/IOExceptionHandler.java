package de.atlasmc.io;

import de.atlasmc.io.connection.ConnectionHandler;

/**
 * Interface for handling exception in protocols e.g. exception while packet read
 */
@FunctionalInterface
public interface IOExceptionHandler {
	
	/**
	 * Exception handler that will log the exception and return true
	 */
	public static final IOExceptionHandler LOG_ONLY = (con, cause) -> { 
		con.getLogger().error("Error in connection: " + con.getRemoteAddress(), cause);
		return false;
	};
	
	/**
	 * Handles the exception. If this handler returns false the connection will automatically close.
	 * @param con the connection the exception occurred
	 * @param cause the exception
	 * @return true if handled
	 */
	boolean handle(ConnectionHandler con, Throwable cause);

}
