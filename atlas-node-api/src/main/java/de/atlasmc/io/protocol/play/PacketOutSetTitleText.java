package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_TITLE_TEXT)
public class PacketOutSetTitleText extends AbstractPacket implements PacketPlayOut {

	public String title;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_TITLE_TEXT;
	}
	
}
