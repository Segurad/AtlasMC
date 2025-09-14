package de.atlasmc.node.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(packetID = PacketStatus.IN_PING, definition = "ping_request")
public class PacketInPing extends AbstractPacket implements PacketStatus, PacketInbound {
	
	public long ping;
	
	@Override
	public int getDefaultID() {
		return IN_PING;
	}

}
