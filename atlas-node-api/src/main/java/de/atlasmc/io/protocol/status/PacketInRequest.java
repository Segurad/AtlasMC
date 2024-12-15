package de.atlasmc.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(packetID = PacketStatus.IN_REQUEST, definition = "status_request")
public class PacketInRequest extends AbstractPacket implements PacketStatus, PacketInbound {
	
	@Override
	public int getDefaultID() {
		return IN_REQUEST;
	}

}
