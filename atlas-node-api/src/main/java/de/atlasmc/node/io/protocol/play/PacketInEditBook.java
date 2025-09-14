package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_EDIT_BOOK, definition = "edit_book")
public class PacketInEditBook extends AbstractPacket implements PacketPlayIn {
	
	public int slot;
	public String title;
	public String[] pages;
	
	@Override
	public int getDefaultID() {
		return IN_EDIT_BOOK;
	}

}
