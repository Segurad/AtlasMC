package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketKnownPacks;

@DefaultPacketID(packetID = PacketConfiguration.OUT_KNOWN_PACKS, definition = "select_known_packs")
public class ClientboundKnownPacks extends AbstractPacketKnownPacks implements PacketConfigurationClientbound {

	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_KNOWN_PACKS;
	}

}
