package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_ENTITY_EVENT, definition = "entity_event")
public class PacketOutEntityEvent extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public int status;
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_EVENT;
	}

}
