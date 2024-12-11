package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_PING_RESPONSE, definition = "pong_response")
public class PacketOutPingResponse extends AbstractPacket implements PacketPlayOut {
	
	public long payload;
	
	@Override
	public int getDefaultID() {
		return OUT_PING_RESPONSE;
	}
	
}
