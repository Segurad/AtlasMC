package de.atlasmc.io.protocol.configuration;

import de.atlasmc.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketConfiguration.IN_RESOURCE_PACK)
public class PacketInResourcePack extends AbstractPacket implements PacketConfigurationIn {

	public ResourcePackStatus status;
	
	@Override
	public int getDefaultID() {
		return IN_RESOURCE_PACK;
	}
	
}
