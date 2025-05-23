package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketServerLinks;

@DefaultPacketID(packetID = PacketPlay.OUT_SERVER_LINKS, definition = "server_links")
public class PacketOutServerLinks extends AbstractPacketServerLinks implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_SERVER_LINKS;
	}
	
}
