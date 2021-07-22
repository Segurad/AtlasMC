package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_NAME_ITEM)
public interface PacketInNameItem extends PacketPlay, PacketInbound {
	
	public String getItemName();

	@Override
	default int getDefaultID() {
		return IN_NAME_ITEM;
	}
	
}
