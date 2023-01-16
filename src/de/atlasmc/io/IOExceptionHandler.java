package de.atlasmc.io;

import de.atlascore.io.ConnectionHandler;

/**
 * Interface for handling exception in protocols e.g. exception while packet read
 */
@FunctionalInterface
public interface IOExceptionHandler {
	
	public static IOExceptionHandler UNHANDLED = (con, cause) -> { return false; };
	
	/**
	 * 
	 * @param con the connection the exception occurred
	 * @param cause the exception
	 * @return true if handled
	 */
	public boolean handle(ConnectionHandler con, Throwable cause);

}
