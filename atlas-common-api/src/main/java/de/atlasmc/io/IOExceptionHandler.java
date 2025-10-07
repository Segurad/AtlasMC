package de.atlasmc.io;

import de.atlasmc.io.connection.ConnectionHandler;

/**
 * Interface for handling exception in protocols e.g. exception while packet read
 */
@FunctionalInterface
public interface IOExceptionHandler {
	
	public static final IOExceptionHandler UNHANDLED = (con, cause) -> { return false; };
	
	/**
	 * 
	 * @param con the connection the exception occurred
	 * @param cause the exception
	 * @return true if handled
	 */
	boolean handle(ConnectionHandler con, Throwable cause);

}
