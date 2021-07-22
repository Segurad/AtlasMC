package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_USE_ITEM)
public interface PacketInUseItem extends PacketPlay, PacketInbound {
	
	public int getHand();

	@Override
	default int getDefaultID() {
		return IN_USE_ITEM;
	}
	
}
