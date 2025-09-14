package de.atlasmc.node.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_SUBTITLE_TEXT, definition = "set_subtitle_text")
public class PacketOutSetSubtitleText extends AbstractPacket implements PacketPlayOut {

	public Chat subtitle;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_SUBTITLE_TEXT;
	}
	
}
