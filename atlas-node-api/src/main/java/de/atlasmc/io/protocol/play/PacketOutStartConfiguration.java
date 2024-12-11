package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_START_CONFIGURATION, definition = "start_configuration")
public class PacketOutStartConfiguration extends AbstractPacket implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_START_CONFIGURATION;
	}

}
