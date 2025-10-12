package de.atlasmc.node.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketStatus.IN_PING, definition = "ping_request")
public class ServerboundPing extends AbstractPacket implements PacketStatusServerbound {
	
	public long ping;
	
	@Override
	public boolean isTerminating() {
		return true;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PING;
	}

}
