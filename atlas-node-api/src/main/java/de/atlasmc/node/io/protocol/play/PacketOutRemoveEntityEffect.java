package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_REMOVE_ENTITY_EFFECT, definition = "remove_mob_effect")
public class PacketOutRemoveEntityEffect extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public int effectID;
	
	@Override
	public int getDefaultID() {
		return OUT_REMOVE_ENTITY_EFFECT;
	}

}
