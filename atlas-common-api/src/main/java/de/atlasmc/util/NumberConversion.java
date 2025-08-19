package de.atlasmc.util;

import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;

/**
 * Collection of some number related Methods useful or not
 */
@ThreadSafe
public class NumberConversion {

	private NumberConversion() {}
	
	/**
	 * 
	 * @param value
	 * @return value or 0
	 */
	public static int toInt(@Nullable Object value) {
		return toInt(value, 0);
	}
	
	/**
	 * 
	 * @param value
	 * @return value or 0
	 */
	public static double toDouble(@Nullable Object value) {
		return toDouble(value, 0);
	}
	
	/**
	 * 
	 * @param value
	 * @return value or 0
	 */
	public static byte toByte(@Nullable Object value) {
		return toByte(value, 0);
	}
	
	/**
	 * 
	 * @param value
	 * @return value or 0
	 */
	public static long toLong(@Nullable Object value) {
		return toLong(value, 0);
	}
	
	/**
	 * 
	 * @param value
	 * @return value or 0
	 */
	public static short toShort(@Nullable Object value) {
		return toShort(value, 0);
	}
	
	/**
	 * 
	 * @param value
	 * @return value or 0
	 */
	public static float toFloat(@Nullable Object value) {
		return toFloat(value, 0);
	}
	
	/**
	 * 
	 * @param value
	 * @param alt number returned when not a number
	 * @return value or alt
	 */
	public static int toInt(@Nullable Object value, int alt) {
		if (value instanceof Number num) {
			return num.intValue();
		}
		try {
			return Integer.parseInt(value.toString());
		} catch(Exception e) {}
		return alt;
	}
	
	/**
	 * 
	 * @param value
	 * @param alt number returned when not a number
	 * @return value or alt
	 */
	public static double toDouble(@Nullable Object value, double alt) {
		if (value instanceof Number num) {
			return num.doubleValue();
		}
		try {
			return Double.parseDouble(value.toString());
		} catch(Exception e) {}
		return alt;
	}
	
	/**
	 * 
	 * @param value
	 * @param alt number returned when not a number
	 * @return value or alt
	 */
	public static byte toByte(@Nullable Object value, int alt) {
		if (value instanceof Number num) {
			return num.byteValue();
		}
		try {
			return Byte.parseByte(value.toString());
		} catch (Exception e) {}
		return (byte) alt;
	}
	
	/**
	 * 
	 * @param value
	 * @param alt number returned when not a number
	 * @return value or alt
	 */
	public static long toLong(@Nullable Object value, long alt) {
		if (value instanceof Number num) {
			return num.longValue();
		}
		try {
			return Long.parseLong(value.toString());
		} catch (Exception e) {}
		return alt;
	}
	
	/**
	 * 
	 * @param value
	 * @param alt number returned when not a number
	 * @return value or alt
	 */
	public static short toShort(@Nullable Object value, int alt) {
		if (value instanceof Number num) {
			return num.shortValue();
		}
		try {
			return Short.parseShort(value.toString());
		} catch (Exception e) {}
		return (short) alt;
	}
	
	/**
	 * 
	 * @param value
	 * @param alt number returned when not a number
	 * @return value or alt
	 */
	public static float toFloat(@Nullable Object value, float alt) {
		if (value instanceof Number num) {
			return num.floatValue();
		}
		try {
			return Float.parseFloat(value.toString());
		} catch (Exception e) {}
		return alt;
	}
	
	/**
	 * Returns whether or not the given value is a number
	 * @param value to check
	 * @return true if value is a number
	 */
	public static boolean isNumber(@Nullable Object value) {
		if (value == null) 
			return false;
		if (value instanceof Number) 
			return true;
		try {
			Double.parseDouble(value.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
