package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketConfiguration.OUT_FINISH_CONFIGURATION)
public class PacketOutFinishConfiguration extends AbstractPacket implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return OUT_FINISH_CONFIGURATION;
	}
	
}
