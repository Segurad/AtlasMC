package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_STEER_VEHICLE)
public class PacketInSteerVehicle extends AbstractPacket implements PacketPlayIn {
	
	private float sideways,forward;
	private int flags;
	
	public float getSideways() {
		return sideways;
	}
	
	public float getForward() {
		return forward;
	}
	
	public int getFlags() {
		return flags;
	}
	
	public void setSideways(float sideways) {
		this.sideways = sideways;
	}
	
	public void setForward(float forward) {
		this.forward = forward;
	}
	
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	@Override
	public int getDefaultID() {
		return IN_STEER_VEHICLE;
	}

}
