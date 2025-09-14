package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.entity.Entity.Animation;

@DefaultPacketID(packetID = PacketPlay.OUT_ENTITY_ANIMATION, definition = "animate")
public class PacketOutEntityAnimation extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public Animation animation;
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_ANIMATION;
	}

}
