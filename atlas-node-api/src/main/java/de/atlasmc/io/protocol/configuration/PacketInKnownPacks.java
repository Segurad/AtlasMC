package de.atlasmc.io.protocol.configuration;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.configuration.PacketOutKnownPacks.PackInfo;

@DefaultPacketID(packetID = PacketConfiguration.IN_KNOWN_PACKS, definition = "select_known_packs")
public class PacketInKnownPacks extends AbstractPacket implements PacketConfigurationIn {
	
	public List<PackInfo> knownPacks;
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.IN_KNOWN_PACKS;
	}
	
}
