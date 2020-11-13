package de.atlasmc.util;

public class Validate {
	
	public static void notNull(Object value, String msg) {
		if (value == null) throw new IllegalArgumentException(msg);
	}
	
	public static void isTrue(boolean value, String msg) {
		if (value == false) throw new IllegalArgumentException(msg);
	}

}
