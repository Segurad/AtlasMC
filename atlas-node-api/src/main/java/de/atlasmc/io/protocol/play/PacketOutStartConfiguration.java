package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_START_CONFIGURATION)
public class PacketOutStartConfiguration extends AbstractPacket implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_START_CONFIGURATION;
	}

}
