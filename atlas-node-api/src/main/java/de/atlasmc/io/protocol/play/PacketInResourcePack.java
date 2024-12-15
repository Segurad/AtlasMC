package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_RESOURCE_PACK, definition = "resource_pack_status")
public class PacketInResourcePack extends AbstractPacket implements PacketPlayIn {
	
	public UUID uuid;
	public ResourcePackStatus status;
	
	@Override
	public int getDefaultID() {
		return IN_RESOURCE_PACK;
	}

}
