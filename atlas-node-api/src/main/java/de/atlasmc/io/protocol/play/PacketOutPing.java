package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_PING)
public class PacketOutPing extends AbstractPacket implements PacketPlayOut {

	public int ping;
	
	@Override
	public int getDefaultID() {
		return OUT_PING;
	}
	
}
