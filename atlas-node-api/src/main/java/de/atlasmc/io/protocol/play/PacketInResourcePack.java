package de.atlasmc.io.protocol.play;

import de.atlasmc.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_RESOURCE_PACK)
public class PacketInResourcePack extends AbstractPacket implements PacketPlayIn {
	
	private ResourcePackStatus status;
	
	public ResourcePackStatus getStatus() {
		return status;
	}
	
	public void setStatus(ResourcePackStatus status) {
		this.status = status;
	}
	
	@Override
	public int getDefaultID() {
		return IN_RESOURCE_PACK;
	}

}
