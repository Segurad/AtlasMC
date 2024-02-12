package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_UPDATE_ENTITY_POSITION)
public class PacketOutUpdateEntityPosition extends AbstractPacket implements PacketPlayOut {

	private int entityID;
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
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_ENTITY_POSITION;
	}
	
}
