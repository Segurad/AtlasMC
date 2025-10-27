package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketText;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_TITLE_TEXT, definition = "set_title_text")
public class PacketOutSetTitleText extends AbstractPacketText implements PacketPlayOut {
	
	@Override
	public int getDefaultID() {
		return OUT_SET_TITLE_TEXT;
	}
	
}
