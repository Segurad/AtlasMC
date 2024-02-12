package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_TELEPORT_TO_ENTITY)
public class PacketInTeleportToEntity extends AbstractPacket implements PacketPlayIn {
	
	private UUID uuid;
	
	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public int getDefaultID() {
		return IN_TELEPORT_TO_ENTITY;
	}

}
