package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketRemoveResourcePack;

@DefaultPacketID(packetID = PacketConfiguration.OUT_REMOVE_RESOURCE_PACK, definition = "resource_pack_pop")
public class ClientboundRemoveResourcePack extends AbstractPacketRemoveResourcePack implements PacketConfigurationClientbound {

	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_REMOVE_RESOURCE_PACK;
	}
	
}
