package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_SET_PLAYER_POSITION_AND_ROTATION)
public class PacketInSetPlayerPositionAndRotation extends AbstractPacket implements PacketPlayIn {
	
	private double x; 
	private double feetY; 
	private double z;
	private boolean onGround;
	private float yaw;
	private float pitch;
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getFeetY() {
		return feetY;
	}
	
	public void setFeetY(double feetY) {
		this.feetY = feetY;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public boolean isOnGround() {
		return onGround;
	}
	
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	/**
	 * Applies all Location changes to the Location
	 * @param loc
	 */
	public void getLocation(SimpleLocation loc) {
		loc.set(x, feetY, z, yaw, pitch);
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_POSITION_AND_ROTATION;
	}

}
