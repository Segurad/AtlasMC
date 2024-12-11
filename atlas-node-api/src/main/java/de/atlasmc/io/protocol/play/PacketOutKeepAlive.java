package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketKeepAlive;

@DefaultPacketID(packetID = PacketPlay.OUT_KEEP_ALIVE, definition = "keep_alive")
public class PacketOutKeepAlive extends AbstractPacketKeepAlive implements PacketPlayOut {
	
	@Override
	public int getDefaultID() {
		return OUT_KEEP_ALIVE;
	}
	
}
