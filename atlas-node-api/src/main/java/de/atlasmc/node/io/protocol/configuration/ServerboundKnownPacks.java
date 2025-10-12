package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketKnownPacks;

@DefaultPacketID(packetID = PacketConfiguration.IN_KNOWN_PACKS, definition = "select_known_packs")
public class ServerboundKnownPacks extends AbstractPacketKnownPacks implements PacketConfigurationServerbound {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.IN_KNOWN_PACKS;
	}
	
}
