package de.atlasmc.node.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketStatus.OUT_PONG, definition = "pong_response")
public class ClientboundPong extends AbstractPacket implements PacketStatusClientbound {
	
	public long pong;
	
	@Override
	public boolean isTerminating() {
		return true;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_PONG;
	}

}
