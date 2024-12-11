package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_TITLE_ANIMATION_TIMES, definition = "set_titles_animation")
public class PacketOutSetTitleAnimationTimes extends AbstractPacket implements PacketPlayOut {
	
	public int fadeIn;
	public int stay;
	public int fadeOut;

	@Override
	public int getDefaultID() {
		return OUT_SET_TITLE_ANIMATION_TIMES;
	}

}
