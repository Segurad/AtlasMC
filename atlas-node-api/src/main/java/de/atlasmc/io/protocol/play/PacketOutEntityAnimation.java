package de.atlasmc.io.protocol.play;

import de.atlasmc.entity.Entity.Animation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_ENTITY_ANIMATION, definition = "animate")
public class PacketOutEntityAnimation extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public Animation animation;
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_ANIMATION;
	}

}
