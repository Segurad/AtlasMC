package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.IN_FINISH_CONFIGURATION, definition = "finish_configuration")
public class ServerboundFinishConfiguration extends AbstractPacket implements PacketConfigurationServerbound {
	
	@Override
	public boolean isTerminating() {
		return true;
	}
	
	@Override
	public int getDefaultID() {
		return IN_FINISH_CONFIGURATION;
	}
	
}
