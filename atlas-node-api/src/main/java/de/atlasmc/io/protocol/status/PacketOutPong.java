package de.atlasmc.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(packetID = PacketStatus.OUT_PONG, definition = "pong_response")
public class PacketOutPong extends AbstractPacket implements PacketStatus, PacketOutbound {
	
	public long pong;
	
	@Override
	public int getDefaultID() {
		return OUT_PONG;
	}

}
