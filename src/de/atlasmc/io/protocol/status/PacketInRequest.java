package de.atlasmc.io.protocol.status;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketStatus.IN_REQUEST)
public interface PacketInRequest extends PacketStatus, PacketInbound {
	
	@Override
	default int getDefaultID() {
		return IN_REQUEST;
	}

}
