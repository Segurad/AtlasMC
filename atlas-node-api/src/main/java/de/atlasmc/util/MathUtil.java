package de.atlasmc.util;

import org.joml.Vector3d;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.world.World;

public final class MathUtil {
	
	private MathUtil() {}

	public static int floor(double value) {
		int i = (int) value;
		return value > i ? i : i-1;
	}
	
	public static int upper(double value) {
		int i = (int) value;
		return value > i ? i+1 : i;
	}

	public static int getPositionX(long position) {
		return (int) (position >> 38);
	}
	
	public static int getPositionY(long position) {
		return (int) (position << 52 >> 52);
	}
	
	public static int getPositionZ(long position) {
		return (int) (position << 26 >> 38);
	}
	
	public static long toPosition(int x, int y, int z) {
		return ((x & 0x3FFFFFF) << 38) |
		((z & 0x3FFFFFF) << 12) |
		(y & 0xFFF);
	}
	
	/**
	 * 
	 * @param chunkX the chunk x coordinate
	 * @param chunkZ the chunk z coordinate
	 * @return
	 */
	public static long toChunkPosition(int chunkX, int chunkZ) {
		return (((long) chunkX) << 32) | chunkZ;
	}
	
	public static long coordinatesToChunkPosition(double x, double z) {
		return (((long) (((int)x) >> 4)) << 32) | (((int)z) >> 4);
	}
	
	public static long coordinatesToChunkPosition(int x, int z) {
		return (((long) (x >> 4)) << 32) | (z >> 4);
	}
	
	public static int toChunkCoordinate(double value) {
		int c = (int) value;
		c >>= 4;
		return c;
	}
	
	public static int toChunkCoordinate(int value) {
		return value >> 4;
	}

	public static long toPosition(double x, double y, double z) {
		return toPosition(floor(x), floor(y),  floor(z));
	}
	
	public static long toPosition(SimpleLocation loc) {
		return toPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public static int toAngle(float value) {
		return floor(value * 256 / 360);
	}
	
	public static float fromAngle(int value) {
		return value * 360 / 256;
	}

	public static short delta(double var0, double var1) {
		return (short) ((var0 * 32 - var1 * 32) * 128);
	}
	
	public static long sectionToPosition(int x, int y, int z) {
		return ((x & 0x3FFFFF) << 42) | (y & 0xFFFFF) | ((z & 0x3FFFFF) << 20);
	}
	
	public static int sectionPositionX(long section) {
		return (int) (section >> 42);
	}
	
	public static int sectionPositionY(long section) {
		long y = section << 42 >> 44;
		return (int) y;
	}
	
	public static int sectionPositionZ(long section) {
		long z = section << 22 >> 42;
		return (int) z;
	}
	
	/**
	 * 
	 * @param blockstate
	 * @param x relative to chunk
	 * @param y relative to chunk
	 * @param z relative to chunk
	 * @return the parameters as single long
	 */
	public static long blockdataPosition(int blockstate, int x, int y, int z) {
		return blockstate << 12 | (x << 8 | y << 4 | z);
	}

	public static Location getLocation(World world, long position) {
		return new Location(world, getPositionX(position), getPositionY(position), getPositionZ(position));
	}
	
	public static Location getLocation(World world, Location loc, long position) {
		return loc.set(world, getPositionX(position), getPositionY(position), getPositionZ(position));
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Vector3d> T getLocation(T loc, long position) {
		return (T) loc.set(getPositionX(position), getPositionY(position), getPositionZ(position));
	}
	
	public static SimpleLocation getLocation(long position) {
		return new SimpleLocation(getPositionX(position), getPositionY(position), getPositionZ(position));
	}
	
	public static float getYaw(double x, double z, double lockX, double lockZ) {
		double dx = lockX-x;
		double dz = lockZ-z;
		float yaw = (float) (-Math.atan2(dx,dz)/Math.PI*180);
		if (yaw < 0) 
			yaw += 360;
		return yaw;
	}
	
	public static float getPitch(double x, double y, double z, double lockX, double lockY, double lockZ) {
		double dx = lockX-x;
		double dy = lockY-y;
		double dz = lockZ-z;
		double r = Math.sqrt( dx*dx + dy*dy + dz*dz);
		return (float) (-Math.asin(dy/r)/Math.PI*180);
	}
	
	public static Vector3d getVector(float yaw, float pitch) {
		return getVector(yaw, pitch, new Vector3d());
	}
	
	public static Vector3d getVector(float yaw, float pitch, Vector3d vec) {
		double cosPitch = Math.sin(Math.toRadians(pitch));
		vec.x = -cosPitch * Math.sin(Math.toRadians(yaw));
		vec.y = -Math.sin(Math.toRadians(pitch));
		vec.z = cosPitch * Math.cos(Math.toRadians(yaw));
		return vec;
	}
	
	/**
	 * Calculates the default Minecraft explosion radius by the explosion power.<br><br>
	 * <code>radius = power / 0.225 * 1.3 * 0.3</code>
	 * @param power
	 * @return radius
	 */
	public float getDefaultExplosionRadius(int power) {
		return power / 0.225f * 1.3f * 0.3f;
	}
	
	/**
	 * Calculates the default Minecraft explosion power by the explosion radius.<br><br>
	 * <code>power = radius / 0.3 / 1.3 * 0.255</code>
	 * @param radius
	 * @return power
	 */
	public int getDefaultExplosionPower(float radius) {
		return (int) (radius / 0.3 / 1.3f * 0.225f);
	}
	
	/**
	 * Returns the number of bits required to represent the given value
	 * @param value
	 * @return bits needed
	 */
	public static int getRequiredBitCount(int value) {
		int bits = 1;
		int mask = 0x01;
		while (mask < value) {
			mask = (mask << 1) + 1;
			bits++;
		}
		return bits;
	}
	
	public static int createBitMask(int numberOfBits) {
		return (1 << numberOfBits) - 1;
	}
	
}
