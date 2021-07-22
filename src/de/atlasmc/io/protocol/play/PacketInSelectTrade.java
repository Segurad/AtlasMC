package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_SELECT_TRADE)
public interface PacketInSelectTrade extends PacketPlay, PacketInbound {
	
	public int getSelectedSlot();
	
	@Override
	default int getDefaultID() {
		return IN_SELECT_TRADE;
	}

}
