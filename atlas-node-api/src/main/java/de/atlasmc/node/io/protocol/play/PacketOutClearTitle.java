package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_CLEAR_TITLE, definition = "clear_titles")
public class PacketOutClearTitle extends AbstractPacket implements PacketPlayOut {
	
	public boolean reset;

	@Override
	public int getDefaultID() {
		return OUT_CLEAR_TITLE;
	}
	
}
