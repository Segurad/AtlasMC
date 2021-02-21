package de.atlasmc.util;

public class Validate {
	
	/**
	 * @param value
	 * @param msg the exception message 
	 * @throws IllegalArgumentException if the value is null
	 */
	public static void notNull(Object value, String msg) {
		if (value == null) throw new IllegalArgumentException(msg);
	}
	
	/**
	 * 
	 * @param value
	 * @param msg the exception message 
	 * @throws IllegalArgumentException if the value is false
	 */
	public static void isTrue(boolean value, String msg) {
		if (value == false) throw new IllegalArgumentException(msg);
	}
	
	/**
	 * 
	 * @param value
	 * @param msg the exception message 
	 * @throws IllegalArgumentException if the value is true
	 */
	public static void isFalse(boolean value, String msg) {
		if (value == true) throw new IllegalArgumentException(msg);
	}

	/**
	 * 
	 * @param num
	 * @param msg the exception message 
	 * @throws IllegalArgumentException if the String is not a number
	 */
	public static void isNumber(String num, String msg) {
		if (!NumberConversion.isNumber(num)) throw new IllegalArgumentException(msg);
	}

}
