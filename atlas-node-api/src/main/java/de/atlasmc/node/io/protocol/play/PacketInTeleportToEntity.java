package de.atlasmc.node.io.protocol.play;

import java.util.UUID;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_TELEPORT_TO_ENTITY, definition = "teleport_to_entity")
public class PacketInTeleportToEntity extends AbstractPacket implements PacketPlayIn {
	
	public UUID uuid;
	
	@Override
	public int getDefaultID() {
		return IN_TELEPORT_TO_ENTITY;
	}

}
