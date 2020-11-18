package de.atlasmc.util;

public class NumberConversion {

	public static int square(int value) {
		return value * value;
	}
	
	public static double square(double value) {
		return value * value;
	}
	
	public static long square(long value) {
		return value * value;
	}
	
	public static int toInt(Object value) {
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		try {
			return Integer.parseInt(value.toString());
		} catch(NumberFormatException e) {} catch (NullPointerException e) {}
		return 0;
	}
	
	public static double toDouble(Object value) {
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		}
		try {
			return Double.parseDouble(value.toString());
		} catch(NumberFormatException e) {} catch (NullPointerException e) {}
		return 0;
	}
	
	public static byte toByte(Object value) {
		if (value instanceof Number) {
			return ((Number) value).byteValue();
		}
		try {
			return Byte.parseByte(value.toString());
		} catch (NumberFormatException e) {} catch (NullPointerException e) {}
		return 0;
	}
	
	public static long toLong(Object value) {
		if (value instanceof Number) {
			return ((Number) value).longValue();
		}
		try {
			return Byte.parseByte(value.toString());
		} catch (NumberFormatException e) {} catch (NullPointerException e) {}
		return 0;
	}
	
	public static short toShort(Object value) {
		if (value instanceof Number) {
			return ((Number) value).shortValue();
		}
		try {
			Short.parseShort(value.toString());
		} catch (NumberFormatException e) {} catch (NullPointerException e) {};
		return 0;
	}
	
	public static float toFloat(Object value) {
		if (value instanceof Number) {
			return ((Number) value).floatValue();
		}
		try {
			return Float.parseFloat(value.toString());
		} catch (NumberFormatException e) {} catch (NullPointerException e) {}
		return 0;
	}
	
	public static boolean isNumber(Object value) {
		if (value == null) return false;
		if (value instanceof Number) return true;
		try {
			Double.parseDouble(value.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
