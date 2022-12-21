package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_ENTITY_VELOCITY)
public class PacketOutEntityVelocity extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private double x, y, z;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public double getVelocityX() {
		return x;
	}
	
	public double getVelocityY() {
		return y;
	}
	
	public double getVelocityZ() {
		return z;
	}
	
	public void setVelocityX(double x) {
		this.x = x;
	}
	
	public void setVelocityY(double y) {
		this.y = y;
	}
	
	public void setVelocityZ(double z) {
		this.z = z;
	}
	
	public void setVelocity(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_VELOCITY;
	}

}
