package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_CLEAR_TITLE)
public class PacketOutClearTitle extends AbstractPacket implements PacketPlayOut {
	
	public boolean reset;

	@Override
	public int getDefaultID() {
		return OUT_CLEAR_TITLE;
	}
	
}
