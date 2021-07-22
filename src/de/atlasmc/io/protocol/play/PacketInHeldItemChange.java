package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_HELD_ITEM_CHANGE)
public interface PacketInHeldItemChange extends PacketPlay, PacketInbound {
	
	public short getSlot();

	@Override
	default int getDefaultID() {
		return IN_HELD_ITEM_CHANGE;
	}
	
}
