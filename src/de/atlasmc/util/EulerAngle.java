package de.atlasmc.util;

public class EulerAngle {

	private float x, y, z;
	
	public EulerAngle(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public EulerAngle() {}

	public EulerAngle(EulerAngle angle) {
		this.x = angle.x;
		this.y = angle.y;
		this.z = angle.z;
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
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}

	public void set(EulerAngle angle) {
		this.x = angle.x;
		this.y = angle.y;
		this.z = angle.z;
	}

	public void copyTo(EulerAngle angle) {
		angle.x = x;
		angle.y = y;
		angle.z = z;
	}

}
