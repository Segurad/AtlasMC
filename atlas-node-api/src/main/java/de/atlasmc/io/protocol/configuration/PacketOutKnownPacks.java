package de.atlasmc.io.protocol.configuration;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.OUT_KNOWN_PACKS, definition = "select_known_packs")
public class PacketOutKnownPacks extends AbstractPacket implements PacketConfigurationOut {
	
	public List<PackInfo> knownPacks;
	
	public static class PackInfo {
		
		public String namespace;
		public String id;
		public String version;
		
	}

	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_KNOWN_PACKS;
	}

}
