package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_RESOURCE_PACKET_STATUS)
public interface PacketInResourcePackStatus extends PacketPlay, PacketInbound {
	
	public int getResult();
	
	@Override
	default int getDefaultID() {
		return IN_RESOURCE_PACKET_STATUS;
	}

}
