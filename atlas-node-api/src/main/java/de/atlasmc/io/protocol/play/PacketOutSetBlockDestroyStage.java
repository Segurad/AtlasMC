package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_BLOCK_DESTROY_STAGE)
public class PacketOutSetBlockDestroyStage extends AbstractPacket implements PacketPlayOut {
	
	private int entityID, stage;
	private long position;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public int getStage() {
		return stage;
	}
	
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_BLOCK_DESTROY_STAGE;
	}

}
