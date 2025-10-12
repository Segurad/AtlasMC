package de.atlasmc.node.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketStatus.IN_REQUEST, definition = "status_request")
public class ServerboundRequest extends AbstractPacket implements PacketStatusServerbound {
	
	@Override
	public int getDefaultID() {
		return IN_REQUEST;
	}

}
