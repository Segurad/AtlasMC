package de.atlasmc.util;

public class EulerAngle implements Cloneable {

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

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
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
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
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
	
	public EulerAngle clone() {
		return new EulerAngle(x, y, z);
	}

}
