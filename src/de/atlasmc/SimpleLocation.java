package de.atlasmc;

import de.atlasmc.util.MathUtil;
import de.atlasmc.world.World;

/**
 * Simplified version of {@link Location} that does not store a {@link World}
 */
public class SimpleLocation implements Cloneable {

	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;

	public SimpleLocation(SimpleLocation loc) {
		this(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
	}

	public SimpleLocation(double x, double y, double z) {
		this(x, y, z, 0, 0);
	}

	public SimpleLocation(double x, double y, double z, float yaw, float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	/**
	 * Creates a new {@link SimpleLocation} 
	 * <li>x - 0 
	 * <li>y - 0 
	 * <li>z - 0
	 * <li>yaw - 0.0 
	 * <li>pitch - 0.0
	 */
	public SimpleLocation() {
		this(0, 0, 0);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	public void addX(double x) {
		this.x += x;
	}
	
	public void addY(double y) {
		this.y += y;
	}
	
	public void addZ(double z) {
		this.z += z;
	}

	/**
	 * Returns the floor of X
	 * @return blockX
	 */
	public int getBlockX() {
		return MathUtil.floor(x);
	}

	/**
	 * Returns the floor of Y
	 * @return blockY
	 */
	public int getBlockY() {
		return MathUtil.floor(y);
	}

	/**
	 * Returns the floor of Z
	 * @return blockZ
	 */
	public int getBlockZ() {
		return MathUtil.floor(z);
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public SimpleLocation clone() {
		SimpleLocation clone = null;
		try {
			clone = (SimpleLocation) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	/**
	 * 
	 * @param loc
	 * @return this location
	 */
	public SimpleLocation add(SimpleLocation loc) {
		x += loc.getX();
		y += loc.getY();
		z += loc.getZ();
		return this;
	}
	
	public SimpleLocation add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public SimpleLocation scale(double scale) {
		x *= scale;
		y *= scale;
		z *= scale;
		return this;
	};

	public SimpleLocation sub(SimpleLocation loc) {
		x -= loc.getX();
		y -= loc.getY();
		z -= loc.getZ();
		return this;
	}

	/**
	 * Rotates the Location around 0 0 0
	 * @param rotation the rotation value
	 * @return this Location
	 */
	public SimpleLocation rotate(int rotation) {
		final double cos = Math.cos(Math.toRadians(rotation));
		final double sin = Math.sin(Math.toRadians(rotation));
		x = x * cos - z * sin;
		z = x * sin + z * cos;
		return this;
	}

	/**
	 * Returns the distance to the given location.<br>
	 * Consider using {@link #getDistanceSquared(SimpleLocation)}!
	 * @param loc
	 * @return distance
	 */
	public double getDistance(SimpleLocation loc) {
		return Math.sqrt(getDistanceSquared(loc));
	}

	/**
	 * Returns the squared distance to the given location
	 * @param loc
	 * @return squared distance
	 */
	public double getDistanceSquared(SimpleLocation loc) {
		return (Math.pow((getX() - loc.getX()), 2) + Math.pow((getY() - loc.getY()), 2) + Math.pow((getZ() - loc.getZ()), 2));
	}

	/**
	 * 
	 * @param loc
	 * @return this location
	 */
	public SimpleLocation setLocation(SimpleLocation loc) {
		x = loc.x;
		y = loc.y;
		z = loc.z;
		pitch = loc.pitch;
		yaw = loc.yaw;
		return this;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return this location
	 */
	public SimpleLocation setLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param pitch
	 * @param yaw
	 * @return this location
	 */
	public SimpleLocation setLocation(double x, double y, double z, float yaw, float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		return this;
	}
	
	public SimpleLocation copyTo(SimpleLocation loc) {
		loc.x = x;
		loc.y = y;
		loc.z = z;
		loc.pitch = pitch;
		loc.yaw = yaw;
		return loc;	
	}
	
	/**
	 * Converts all values to integer values
	 * @return this Location
	 */
	public SimpleLocation convertToBlock() {
		this.x = getBlockX();
		this.y = getBlockY();
		this.z = getBlockZ();
		return this;
	}
	
	/**
	 * Converts pitch and yaw to a Vector
	 * @return vector
	 */
	public Vector getDirection() {
		return MathUtil.getVector(yaw, pitch);
	}
	
	/**
	 * Converts pitch and yaw to a Vector
	 * @param vec Vector used to store the direction
	 * @return the given Vector
	 */
	public Vector getDirection(Vector vec) {
		return MathUtil.getVector(yaw, pitch, vec);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(pitch);
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Float.floatToIntBits(yaw);
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof SimpleLocation)
			return false;
		SimpleLocation other = (SimpleLocation) obj;
		return pitch == other.pitch
				&& x == other.x
				&& y == other.y
				&& yaw == other.yaw
				&& z == other.z;
	}
	
}
