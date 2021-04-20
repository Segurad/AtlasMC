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

	public static int getPositionX(long position) {
		return (int) (position >> 38);
	}
	
	public static int getPositionY(long position) {
		return (int) (position & 0xFFF);
	}
	
	public static int getPositionZ(long position) {
		return (int) (position << 26 >> 38);
	}
	
	public static long toPosition(int x, int y, int z) {
		return ((x & 0x3FFFFFF) << 38) |
		((z & 0x3FFFFFF) << 12) |
		(y & 0xFFF);
	}

	public static long toPosition(double x, double y, double z) {
		return toPosition((int) x, (int) y,  (int) z);
	}

	public static int toAngle(float value) {
		return Math.round(value * 256 / 360);
	}
	
	public static float fromAngle(int value) {
		return value * 360 / 256;
	}
}
