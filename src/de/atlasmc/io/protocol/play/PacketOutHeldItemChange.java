package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_HELD_ITEM_CHANGE)
public class PacketOutHeldItemChange extends AbstractPacket implements PacketPlayOut {
	
	private int slot; 
	
	public int getSlot() {
		return slot;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_HELD_ITEM_CHANGE;
	}

}
