package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_ROTATION)
public class PacketInSetPlayerRotation extends AbstractPacket implements PacketPlayIn {
	
	private float yaw;
	private float pitch;
	private boolean onGround;
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	public boolean isOnGround() {
		return onGround;
	}
	
	/**
	 * Applies all Location changes to the Location
	 * @param loc
	 */
	public void getLocation(SimpleLocation loc) {
		loc.yaw = yaw;
		loc.pitch = pitch;
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_ROTATION;
	}

}
