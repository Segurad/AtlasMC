package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_SELECT_TRADE)
public class PacketInSelectTrade extends AbstractPacket implements PacketPlayIn {
	
	private int selectedSlot;
	
	public int getSelectedSlot() {
		return selectedSlot;
	}
	
	public void setSelectedSlot(int selectedSlot) {
		this.selectedSlot = selectedSlot;
	}
	
	@Override
	public int getDefaultID() {
		return IN_SELECT_TRADE;
	}

}
