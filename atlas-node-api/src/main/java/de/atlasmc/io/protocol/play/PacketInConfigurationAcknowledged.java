package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CONFIGURATION_ACKNOWLEDGED)
public class PacketInConfigurationAcknowledged extends AbstractPacket implements PacketPlayIn {
	
	@Override
	public int getDefaultID() {
		return IN_CONFIGURATION_ACKNOWLEDGED;
	}

}
