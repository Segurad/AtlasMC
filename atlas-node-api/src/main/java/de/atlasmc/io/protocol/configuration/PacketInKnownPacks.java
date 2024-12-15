package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketKnownPacks;

@DefaultPacketID(packetID = PacketConfiguration.IN_KNOWN_PACKS, definition = "select_known_packs")
public class PacketInKnownPacks extends AbstractPacketKnownPacks implements PacketConfigurationIn {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.IN_KNOWN_PACKS;
	}
	
}
