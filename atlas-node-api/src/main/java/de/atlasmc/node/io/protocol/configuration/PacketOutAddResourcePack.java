package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketAddResourcePack;

@DefaultPacketID(packetID = PacketConfiguration.OUT_ADD_RESOURCE_PACK, definition = "resource_pack_push")
public class PacketOutAddResourcePack extends AbstractPacketAddResourcePack implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return OUT_ADD_RESOURCE_PACK;
	}
	
}
