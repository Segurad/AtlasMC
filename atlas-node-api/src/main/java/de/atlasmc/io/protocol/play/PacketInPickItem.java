package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_PICK_ITEM)
public class PacketInPickItem extends AbstractPacket implements PacketPlayIn {
	
	private int slotToUse;
	
	public int getSlotToUse() {
		return slotToUse;
	}
	
	public void setSlotToUse(int slotToUse) {
		this.slotToUse = slotToUse;
	}

	@Override
	public int getDefaultID() {
		return IN_PICK_ITEM;
	}
	
}
