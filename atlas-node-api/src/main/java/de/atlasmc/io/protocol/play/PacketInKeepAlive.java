package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketKeepAlive;

@DefaultPacketID(packetID = PacketPlay.IN_KEEP_ALIVE, definition = "keep_alive")
public class PacketInKeepAlive extends AbstractPacketKeepAlive implements PacketPlayIn {

	@Override
	public int getDefaultID() {
		return IN_KEEP_ALIVE;
	}
	
}
