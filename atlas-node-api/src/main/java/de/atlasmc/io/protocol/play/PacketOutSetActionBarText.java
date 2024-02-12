package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_ACTION_BAR_TEXT)
public class PacketOutSetActionBarText extends AbstractPacket implements PacketPlayOut {

	public String text;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_ACTION_BAR_TEXT;
	}
	
}
