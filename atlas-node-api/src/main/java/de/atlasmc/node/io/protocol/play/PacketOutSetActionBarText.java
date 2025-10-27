package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketText;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_ACTION_BAR_TEXT, definition = "set_action_bar_text")
public class PacketOutSetActionBarText extends AbstractPacketText implements PacketPlayOut {
	
	@Override
	public int getDefaultID() {
		return OUT_SET_ACTION_BAR_TEXT;
	}
	
}
