package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_LOOK_AT)
public class PacketOutLookAt extends AbstractPacket implements PacketPlayOut {
	
	private boolean aimWithEyes;
	private boolean aimAtEyes;
	private boolean hasEntity;
	private double x;
	private double y;
	private double z;
	private int entityID;
	
	/**
	 * 
	 * @return whether or not player should aim with his eyes or feet
	 */
	public boolean getAimWithEyes() {
		return aimWithEyes;
	}
	
	public void setAimWithEyes(boolean aimWithEyes) {
		this.aimWithEyes = aimWithEyes;
	}
	
	/**
	 * 
	 * @return whether or not the the player is facing towards an entity
	 */
	public boolean hasEntity() {
		return hasEntity;
	}

	public void setHasEntity(boolean hasEntity) {
		this.hasEntity = hasEntity;
	}
	
	/**
	 * 
	 * @return same as getAimWithEyes but with the entity
	 */
	public boolean getAimAtEyes() {
		return aimAtEyes;
	}
	
	public void setAimAtEyes(boolean aimAtEyes) {
		this.aimAtEyes = aimAtEyes;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public int getEntityID() {
		return entityID;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	@Override
	public int getDefaultID() {
		return OUT_LOOK_AT;
	}

}
