package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PING_REQUEST, definition = "ping_request")
public class PacketInPingRequest extends AbstractPacket implements PacketPlayIn {

	public long ping;
	
	@Override
	public int getDefaultID() {
		return IN_PING_REQUEST;
	}
	
}
