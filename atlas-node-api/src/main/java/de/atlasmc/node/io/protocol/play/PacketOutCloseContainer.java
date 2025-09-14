package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_CLOSE_CONTAINER, definition = "container_close")
public class PacketOutCloseContainer extends AbstractPacket implements PacketPlayOut {
	
	public int windowID;
	
	@Override
	public int getDefaultID() {
		return OUT_CLOSE_CONTAINER;
	}

}
