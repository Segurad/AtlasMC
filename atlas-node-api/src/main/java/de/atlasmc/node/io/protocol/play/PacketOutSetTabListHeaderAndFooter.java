package de.atlasmc.node.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_TAB_LIST_HEADER_AND_FOOTER, definition = "tab_list")
public class PacketOutSetTabListHeaderAndFooter extends AbstractPacket implements PacketPlayOut {
	
	public Chat header;
	public Chat footer;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_TAB_LIST_HEADER_AND_FOOTER;
	}

}
