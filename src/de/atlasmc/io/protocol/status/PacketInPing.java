package de.atlasmc.io.protocol.status;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketStatus.IN_PING)
public interface PacketInPing extends PacketStatus, PacketInbound {
	
	public long getPing();
	
	@Override
	default int getDefaultID() {
		return IN_PING;
	}

}
