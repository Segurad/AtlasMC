package de.atlasmc.io.protocol.configuration;

import java.util.UUID;

import de.atlasmc.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.IN_RESOURCE_PACK, definition = "resource_pack")
public class PacketInResourcePack extends AbstractPacket implements PacketConfigurationIn {

	public UUID uuid;
	public ResourcePackStatus status;
	
	@Override
	public int getDefaultID() {
		return IN_RESOURCE_PACK;
	}
	
}
