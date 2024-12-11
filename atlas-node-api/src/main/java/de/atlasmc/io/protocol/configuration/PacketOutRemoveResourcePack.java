package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketRemoveResourcePack;

@DefaultPacketID(packetID = PacketConfiguration.OUT_REMOVE_RESOURCE_PACK, definition = "resource_pack_pop")
public class PacketOutRemoveResourcePack extends AbstractPacketRemoveResourcePack implements PacketConfigurationOut {

	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_REMOVE_RESOURCE_PACK;
	}
	
}
