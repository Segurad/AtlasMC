package de.atlasmc.node.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.configuration.file.JsonConfiguration;

@DefaultPacketID(packetID = PacketStatus.OUT_RESPONSE, definition = "status_response")
public class ClientboundResponse extends AbstractPacket implements PacketStatusClientbound {
	
	public JsonConfiguration response;
	
	@Override
	public int getDefaultID() {
		return OUT_RESPONSE;
	}

}
