package de.atlasmc.io.protocol.status;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketStatus.OUT_PONG)
public interface PacketOutPong extends PacketStatus, PacketOutbound {
	
	@Override
	default int getDefaultID() {
		return OUT_PONG;
	}

}
