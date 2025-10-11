package de.atlasmc.log;

import de.atlasmc.util.annotation.NotNull;

/**
 * A logger
 */
public interface Log {
	
	Level getLevel();
	
	@NotNull
	String getName();
	
	boolean isSendToConsole();
	
	void sendToConsole(boolean console);
	
	void error(String message);
	
	void error(String message, Throwable cause);
	
	void error(String message, Object p0);
	
	void error(String message, Object p0, Object p1);
	
	void error(String message, Object p0, Object p1, Object p2);
	
	void error(String message, Object p0, Object p1, Object p2, Object p3);
	
	void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4);
	
	void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);
	
	void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

	void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

	void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

	void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);
	
	void error(String message, Object... params);
	
	void error(Throwable cause);
	
	void info(String message);
	
	void info(String message, Throwable cause);
	
	void info(String message, Object p0);
	
	void info(String message, Object p0, Object p1);
	
	void info(String message, Object p0, Object p1, Object p2);
	
	void info(String message, Object p0, Object p1, Object p2, Object p3);
	
	void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4);
	
	void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);
	
	void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

	void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

	void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

	void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);
	
	void info(String message, Object... params);
	
	void info(Throwable cause);
	
	void warn(String message);
	
	void warn(String message, Throwable cause);
	
	void warn(String message, Object p0);
	
	void warn(String message, Object p0, Object p1);
	
	void warn(String message, Object p0, Object p1, Object p2);
	
	void warn(String message, Object p0, Object p1, Object p2, Object p3);
	
	void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4);
	
	void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);
	
	void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

	void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

	void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

	void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);
	
	void warn(String message, Object... params);
	
	void warn(Throwable cause);
	
	void debug(String message);
	
	void debug(String message, Throwable cause);
	
	void debug(String message, Object p0);
	
	void debug(String message, Object p0, Object p1);
	
	void debug(String message, Object p0, Object p1, Object p2);
	
	void debug(String message, Object p0, Object p1, Object p2, Object p3);
	
	void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4);
	
	void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);
	
	void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

	void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

	void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

	void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);
	
	void debug(String message, Object... params);
	
	void debug(Throwable cause);
	
	void log(Level level, String message);
	
	void log(Level level, String message, Throwable cause);
	
	void log(Level level, String message, Object p0);
	
	void log(Level level, String message, Object p0, Object p1);
	
	void log(Level level, String message, Object p0, Object p1, Object p2);
	
	void log(Level level, String message, Object p0, Object p1, Object p2, Object p3);
	
	void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4);
	
	void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5);
	
	void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6);

	void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7);

	void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8);

	void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9);
	
	void log(Level level, String message, Object... params);
	
	void log(Level level, Throwable cause);
	
	public static enum Level {
		
	    OFF,
	    FATAL,
	    ERROR,
	    WARN,
	    INFO,
	    DEBUG,
	    TRACE,
	    ALL;
		
	}

}
