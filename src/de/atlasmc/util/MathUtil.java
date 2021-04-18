package de.atlasmc.util;

public class MathUtil {
	
	private MathUtil() {}
	
	/**
	 * 
	 * @param original
	 * @param min
	 * @param max
	 * @return the original if it is in range otherwise it will return the min or max
	 */
	public static double getInRange(double original, double min, double max) {
		if (original > max)
			return max;
		if (original < min) {
			return min;
		}
		return original;
	}

}
