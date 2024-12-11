package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_TITLE_TEXT, definition = "set_title_text")
public class PacketOutSetTitleText extends AbstractPacket implements PacketPlayOut {

	public Chat title;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_TITLE_TEXT;
	}
	
}
