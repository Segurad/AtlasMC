package de.atlasmc.core.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.atlasmc.log.Log;
import de.atlasmc.log.LogHandler;

public class CoreLogHandler implements LogHandler {

	@Override
	public Log getLogger(Class<?> clazz, String group) {
		return getLogger(clazz.getSimpleName(), group);
	}

	@Override
	public Log getLogger(String name, String group) {
		return new CoreLog(name, getLog4jLogger(group));
	}
	
	private synchronized Logger getLog4jLogger(String name) {
		return LogManager.getLogger(name);
	}

}
