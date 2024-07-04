package de.atlasmc.log;

public interface LogHandler {
	
	Log getLogger(Class<?> clazz, String group);
	
	Log getLogger(String name, String group);

}
