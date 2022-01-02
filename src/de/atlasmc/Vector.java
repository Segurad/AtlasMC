package de.atlasmc;

public class Vector implements Cloneable {
	
	private double x,y,z;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Creates a new Vector(0, 0, 0)
	 */
	public Vector() {
		this(0, 0, 0);
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

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public Vector add(Vector vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}
	
	public Vector add(SimpleLocation loc) {
		x += loc.getX();
		y += loc.getY();
		z += loc.getZ();
		return this;
	}
	
	public Vector sub(Vector vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}
	
	public Vector sub(SimpleLocation loc) {
		x -= loc.getX();
		y -= loc.getY();
		z -= loc.getZ();
		return this;
	}
	
	public Vector multiply(int num) {
		x *= num;
		y *= num;
		z *= num;
		return this;
	}
	
	public Vector multiply(long num) {
		x *= num;
		y *= num;
		z *= num;
		return this;
	}
	
	public Vector multiply(double num) {
		x *= num;
		y *= num;
		z *= num;
		return this;
	}
	
	public Vector multiply(float num) {
		x *= num;
		y *= num;
		z *= num;
		return this;
	}
	
	public Vector multiply(Vector vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}
	
	public double dot(Vector vec) {
		return x*vec.x + y*vec.y + z*vec.z;
	}
	
	public Vector cross(Vector vec) {
		final double x = this.x, y = this.y, z = this.z;
		this.x = y*vec.z - z*vec.y;
		this.y = z*vec.x - x*vec.z;
		this.z = x*vec.y - y*vec.x;
		return this;
	}
	
	@Override
	public Vector clone() {
		try {
			return (Vector) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector unit() {
		final double length = length();
		x /= length;
		y /= length;
		z /= length;
		return this;
	}
	
	public double length() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public double lengthSquared() {
		return x*x + y*y + z*z;
	}

}
