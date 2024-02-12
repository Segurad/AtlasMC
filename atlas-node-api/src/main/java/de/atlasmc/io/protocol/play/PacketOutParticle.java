package de.atlasmc.io.protocol.play;

import de.atlasmc.Particle;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_PARTICLE)
public class PacketOutParticle extends AbstractPacket implements PacketPlayOut {
	
	private Particle particle;
	private int count;
	private double x, y, z;
	private float offX, offY, offZ, maxSpeed;
	private Object data;
	private boolean longDistance;
	
	public Particle getParticle() {
		return particle;
	}

	public int getCount() {
		return count;
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

	public float getOffX() {
		return offX;
	}

	public float getOffY() {
		return offY;
	}

	public float getOffZ() {
		return offZ;
	}

	public Object getData() {
		return data;
	}

	public boolean isLongDistance() {
		return longDistance;
	}

	public void setParticle(Particle particle) {
		this.particle = particle;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void setLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setOffX(float offX) {
		this.offX = offX;
	}

	public void setOffY(float offY) {
		this.offY = offY;
	}

	public void setOffZ(float offZ) {
		this.offZ = offZ;
	}
	
	public void setOffset(float offX, float offY, float offZ) {
		this.offX = offX;
		this.offY = offY;
		this.offZ = offZ;
	}
	
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setLongDistance(boolean longDistance) {
		this.longDistance = longDistance;
	}

	@Override
	public int getDefaultID() {
		return OUT_PARTICLE;
	}

}
