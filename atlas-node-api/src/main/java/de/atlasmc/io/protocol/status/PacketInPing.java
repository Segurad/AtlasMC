package de.atlasmc.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketStatus.IN_PING)
public class PacketInPing extends AbstractPacket implements PacketStatus, PacketInbound {
	
	public long ping;
	
	@Override
	public int getDefaultID() {
		return IN_PING;
	}

}
