package de.atlasmc.io.protocol.status;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketStatus.OUT_RESPONSE)
public interface PacketOutResponse extends PacketStatus, PacketOutbound {
	
	@Override
	default int getDefaultID() {
		return OUT_RESPONSE;
	}

}
