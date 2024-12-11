package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CLOSE_CONTAINER, definition = "close_container")
public class PacketInCloseContainer extends AbstractPacket implements PacketPlayIn {
	
	public int windowID;
	
	@Override
	public int getDefaultID() {
		return IN_CLOSE_CONTAINER;
	}

}
