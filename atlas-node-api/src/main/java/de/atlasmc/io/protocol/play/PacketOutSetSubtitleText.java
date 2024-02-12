package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_SUBTITLE_TEXT)
public class PacketOutSetSubtitleText extends AbstractPacket implements PacketPlayOut {

	public String subtitle;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_SUBTITLE_TEXT;
	}
	
}
