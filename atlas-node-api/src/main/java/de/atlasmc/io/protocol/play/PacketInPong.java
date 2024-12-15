package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketPing;

@DefaultPacketID(packetID = PacketPlay.IN_PONG, definition = "pong")
public class PacketInPong extends AbstractPacketPing implements PacketPlayIn {
	
	@Override
	public int getDefaultID() {
		return PacketPlay.IN_PONG;
	}

}
