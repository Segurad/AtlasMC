package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_TAB_COMPLETE)
public interface PacketInTabComplete extends PacketPlay, PacketInbound {

	public int getTransactionID();
	public String getText();
	
	@Override
	default int getDefaultID() {
		return IN_TAB_COMPLETE;
	}
	
}
