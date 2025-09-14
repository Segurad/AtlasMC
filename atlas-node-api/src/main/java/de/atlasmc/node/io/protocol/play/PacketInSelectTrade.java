package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SELECT_TRADE, definition = "select_trade")
public class PacketInSelectTrade extends AbstractPacket implements PacketPlayIn {
	
	public int selectedSlot;
	
	@Override
	public int getDefaultID() {
		return IN_SELECT_TRADE;
	}

}
