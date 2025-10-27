package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketText;

@DefaultPacketID(packetID = PacketConfiguration.OUT_DISCONNECT, definition = "disconnect")
public class ClientboundDisconnect extends AbstractPacketText implements PacketConfigurationClientbound {
	
	@Override
	public boolean isTerminating() {
		return true;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}
	
}
