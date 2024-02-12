package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_PING_RESPONSE)
public class PacketOutPingResponse extends AbstractPacket implements PacketPlayOut {

	public long ping;
	
	@Override
	public int getDefaultID() {
		return OUT_PING_RESPONSE;
	}
	
}
