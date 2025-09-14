package de.atlasmc.node.io.protocol.configuration;

import java.util.UUID;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;

@DefaultPacketID(packetID = PacketConfiguration.IN_RESOURCE_PACK, definition = "resource_pack")
public class PacketInResourcePack extends AbstractPacket implements PacketConfigurationIn {

	public UUID uuid;
	public ResourcePackStatus status;
	
	@Override
	public int getDefaultID() {
		return IN_RESOURCE_PACK;
	}
	
}
