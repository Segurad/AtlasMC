package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_PLAYER_ROTATION)
public class PacketInPlayerRotation extends AbstractPacket implements PacketPlayIn {
	
	private float yaw, pitch;
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
		loc.setYaw(yaw);
		loc.setPitch(pitch);
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_ROTATION;
	}

}
