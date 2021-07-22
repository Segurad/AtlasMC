package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_PICK_ITEM)
public interface PacketInPickItem extends PacketPlay, PacketInbound {
	
	public int getSlotToUse();

	@Override
	default int getDefaultID() {
		return IN_PICK_ITEM;
	}
	
}
