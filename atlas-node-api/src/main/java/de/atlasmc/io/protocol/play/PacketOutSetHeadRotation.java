package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_HEAD_ROTATION)
public class PacketOutSetHeadRotation extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private float yaw;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}	
	
	@Override
	public int getDefaultID() {
		return OUT_SET_HEAD_ROTATION;
	}

}
