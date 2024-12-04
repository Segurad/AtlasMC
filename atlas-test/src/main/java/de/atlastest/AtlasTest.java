package de.atlastest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import com.google.gson.stream.JsonReader;

public class AtlasTest {
	
	/**
	 * Random string for testing purpose
	 */
	public static final String RANDOM_TEST_STRING = "qMJrR8Fw6OBjQu7C1p0TBqaBFTVuT15nyfoJmmyYdnLSWBwRKkrTgFW0T66in53y";
	
	public static final String VERSION_PATH = "/1_20_2/";
	
	public static JsonReader getJsonResourceReader(String path) throws FileNotFoundException {
		return getJsonResourceReader(path, getCaller());
	}
	
	public static JsonReader getJsonResourceReader(String path, Class<?> clazz) throws FileNotFoundException {
		path = AtlasTest.VERSION_PATH + path;
		URL resource = clazz.getResource(path);
		if (resource == null) {
			throw new FileNotFoundException(path);
		}
		File blocksFile = new File(resource.getFile());
		JsonReader reader = new JsonReader(new FileReader(blocksFile));
		return reader;
	}
	
	public static Class<?> getCaller() {
		Thread thread = Thread.currentThread();
		StackTraceElement[] stack = thread.getStackTrace();
		String rawcaller = stack[3].getClassName();
		Class<?> caller;
		try {
			caller = Class.forName(rawcaller);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Caller not found: " + rawcaller);
		}
		return caller;
	}

}
