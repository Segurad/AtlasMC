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
	
	@Override
	public Vector clone() {
		try {
			return (Vector) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void normalize() {
		// norm vec
		
	}

}
