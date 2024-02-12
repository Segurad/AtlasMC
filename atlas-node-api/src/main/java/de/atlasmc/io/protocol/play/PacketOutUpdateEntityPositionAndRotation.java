package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_UPDATE_ENTITY_POSITION_AND_ROTATION)
public class PacketOutUpdateEntityPositionAndRotation extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private float yaw, pitch;
	private int deltaX, deltaY, deltaZ;
	private boolean onGround;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public int getDeltaX() {
		return deltaX;
	}
	
	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}
	
	public int getDeltaY() {
		return deltaY;
	}
	
	public void setDeltaY(int deltaY) {
		this.deltaY = deltaY;
	}
	
	public int getDeltaZ() {
		return deltaZ;
	}
	
	public void setDeltaZ(int deltaZ) {
		this.deltaZ = deltaZ;
	}
	
	public boolean isOnGround() {
		return onGround;
	}
	
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	public void setLocation(int deltaX, int deltaY, int deltaZ, float yaw, float pitch) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
		this.yaw = yaw;
		this.pitch = pitch;
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
		return OUT_UPDATE_ENTITY_POSITION_AND_ROTATION;
	}

}
