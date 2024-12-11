package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_ACTION_BAR_TEXT, definition = "set_action_bar_text")
public class PacketOutSetActionBarText extends AbstractPacket implements PacketPlayOut {

	public Chat text;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_ACTION_BAR_TEXT;
	}
	
}
