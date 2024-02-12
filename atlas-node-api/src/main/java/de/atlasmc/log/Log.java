package de.atlasmc.log;

public interface Log {
	
	String getName();
	
	boolean isSendToConsole();
	
	void sendToConsole(boolean console);
	
	void error(String message);
	
	void error(String message, Throwable cause);
	
	void error(String message, Object... params);
	
	void error(Throwable cause);
	
	void info(String message);
	
	void info(String message, Object... params);
	
	void warn(String message);
	
	void warn(String message, Object... params);
	
	void debug(String message);
	
	void debug(String message, Object... params);

}
