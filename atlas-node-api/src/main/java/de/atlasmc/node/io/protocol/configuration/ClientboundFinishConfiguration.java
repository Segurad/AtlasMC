package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.OUT_FINISH_CONFIGURATION, definition = "finish_configuration")
public class ClientboundFinishConfiguration extends AbstractPacket implements PacketConfigurationClientbound {
	
	@Override
	public boolean isTerminating() {
		return true;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_FINISH_CONFIGURATION;
	}
	
}
