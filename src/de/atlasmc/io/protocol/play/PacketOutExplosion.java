package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_EXPLOSION)
public class PacketOutExplosion extends AbstractPacket implements PacketPlayOut {

	private float x, y, z, strength, motionX, motionY, motionZ;
	private byte[] records;

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getStrength() {
		return strength;
	}

	public float getMotionX() {
		return motionX;
	}

	public float getMotionY() {
		return motionY;
	}

	public float getMotionZ() {
		return motionZ;
	}

	public byte[] getRecords() {
		return records;
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

	public void setStrength(float strength) {
		this.strength = strength;
	}

	public void setMotionX(float motionX) {
		this.motionX = motionX;
	}

	public void setMotionY(float motionY) {
		this.motionY = motionY;
	}

	public void setMotionZ(float motionZ) {
		this.motionZ = motionZ;
	}

	public void setRecords(byte[] records) {
		this.records = records;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_EXPLOSION;
	}
	
}
