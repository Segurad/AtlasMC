package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_INTERACT)
public class PacketInInteract extends AbstractPacket implements PacketPlayIn {
	
	private int entityID;
	private int type;
	private int hand;
	private float x;
	private float y;
	private float z;
	private boolean sneaking;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getHand() {
		return hand;
	}
	
	public void setHand(int hand) {
		this.hand = hand;
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
	
	public boolean isSneaking() {
		return sneaking;
	}
	
	public void setSneaking(boolean sneaking) {
		this.sneaking = sneaking;
	}
	
	@Override
	public int getDefaultID() {
		return IN_INTERACT;
	}
	
}
