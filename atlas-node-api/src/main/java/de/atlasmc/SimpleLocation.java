package de.atlasmc;

import org.joml.Vector3d;

import de.atlasmc.util.MathUtil;
import de.atlasmc.world.World;

/**
 * Simplified version of {@link Location} that does not store a {@link World}
 */
public class SimpleLocation extends Vector3d implements Cloneable {

	public float pitch;
	public float yaw;

	public SimpleLocation(SimpleLocation loc) {
		this(loc.x, loc.y, loc.z, loc.yaw, loc.pitch);
	}

	public SimpleLocation(double x, double y, double z) {
		this(x, y, z, 0, 0);
	}

	public SimpleLocation(double x, double y, double z, float yaw, float pitch) {
		super(x, y, z);
		this.pitch = pitch;
		this.yaw = yaw;
	}

	/**
	 * Creates a new {@link SimpleLocation} 
	 * <ul>
	 * <li>x - 0</li>
	 * <li>y - 0</li>
	 * <li>z - 0</li>
	 * <li>yaw - 0.0</li>
	 * <li>pitch - 0.0</li>
	 * </ul>
	 */
	public SimpleLocation() {
		this(0, 0, 0);
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
	public double getDistanceSquared(Vector3d loc) {
		double x = this.x - loc.x;
		double y = this.x - loc.y;
		double z = this.x - loc.z;
		x *= x;
		y *= y;
		z *= z;
		return x + y + z;
	}

	/**
	 * 
	 * @param loc
	 * @return this location
	 */
	public SimpleLocation set(SimpleLocation loc) {
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
	 * @param pitch
	 * @param yaw
	 * @return this location
	 */
	public SimpleLocation set(double x, double y, double z, float yaw, float pitch) {
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
	public Vector3d getDirection() {
		return MathUtil.getVector(yaw, pitch);
	}
	
	/**
	 * Converts pitch and yaw to a Vector
	 * @param vec Vector used to store the direction
	 * @return the given Vector
	 */
	public Vector3d getDirection(Vector3d vec) {
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

	/**
	 * Compares the position and rotation
	 * @return true if is equal
	 */
	public boolean matches(SimpleLocation loc) {
		return x == loc.x && 
				z == loc.z &&
				y == loc.y &&
				yaw == loc.yaw &&
				pitch == loc.pitch;
	}
	
	/**
	 * Compares the position
	 * @param loc
	 * @return true if equal
	 */
	public boolean matchPosition(SimpleLocation loc) {
		return x == loc.x && 
				z == loc.z &&
				y == loc.y;
	}
	
	/**
	 * Returns whether or not this location is invalid.
	 * A locations is invalid if x, y or z is one of the following.
	 * <ul>
	 * <li> {@link Double#NaN}</li>
	 * <li> {@link Double#NEGATIVE_INFINITY}</li>
	 * <li> {@link Double#POSITIVE_INFINITY}</li>
	 * </ul>
	 * @return
	 */
	public boolean isInvalid() {
		return isValid(x) && isValid(y) && isValid(z);
	}
	
	private boolean isValid(double value) {
		return value != Double.NaN && value != Double.NEGATIVE_INFINITY && value != Double.POSITIVE_INFINITY;
	}
	
}
