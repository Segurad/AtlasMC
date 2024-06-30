package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_OPEN_HORSE_SCREEN)
public class PacketOutOpenHorseScreen extends AbstractPacket implements PacketPlayOut {
	
	private int windowID;
	private int slots;
	private int entityID;
	
	public int getWindowID() {
		return windowID;
	}
	
	public int getSlots() {
		return slots;
	}
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}
	
	public void setSlots(int slots) {
		this.slots = slots;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_OPEN_HORSE_SCREEN;
	}

}
