package de.atlasmc;

import java.util.Objects;

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

	public int getBlockX() {
		return (int) x;
	}

	public int getBlockY() {
		return (int) y;
	}

	public int getBlockZ() {
		return (int) z;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public SimpleLocation clone() {
		return new SimpleLocation(this);
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
	 * 
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

	public double getDistance(SimpleLocation loc) {
		return Math.sqrt(getDistanceSquared(loc));
	}

	public double getDistanceSquared(SimpleLocation loc) {
		return (Math.pow((getX() - loc.getX()), 2) + Math.pow((getY() - loc.getY()), 2) + Math.pow((getZ() - loc.getZ()), 2));
	}

	public SimpleLocation move(double x, double y, double z) {
		this.x = this.x + x;
		this.y = this.y + y;
		this.z = this.z + z;
		return this;
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

	@Override
	public int hashCode() {
		return Objects.hash(pitch, x, y, yaw, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleLocation other = (SimpleLocation) obj;
		return pitch == other.pitch
				&& x == other.x
				&& y == other.y
				&& yaw == other.yaw
				&& z == other.z;
	}
	
}
