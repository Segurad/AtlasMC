package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketUpdateTags;

@DefaultPacketID(packetID = PacketConfiguration.OUT_UPDATE_TAGS, definition = "update_tags")
public class PacketOutUpdateTags extends AbstractPacketUpdateTags implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_TAGS;
	}
	
}
