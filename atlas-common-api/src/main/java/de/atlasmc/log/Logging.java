package de.atlasmc.log;

public class Logging {
	
	private static volatile LogHandler HANDLER;
	
	public static Log getLogger(String name, String group) {
		return HANDLER.getLogger(name, group);
	}
	
	public static void init(LogHandler handler) {
		if (handler == null)
			throw new IllegalArgumentException("Handler can not be null!");
		synchronized (Logging.class) {
			if (HANDLER != null)
				throw new IllegalStateException("LoggingUtil already initialized!");
			HANDLER = handler;
		}
	}

}
