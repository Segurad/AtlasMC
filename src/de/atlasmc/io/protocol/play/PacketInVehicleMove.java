package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_VEHICLE_MOVE)
public class PacketInVehicleMove extends AbstractPacket implements PacketPlayIn {
	
	private double x,y,z;
	private float yaw,pitch;
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	@Override
	public int getDefaultID() {
		return IN_VEHICLE_MOVE;
	}

}
