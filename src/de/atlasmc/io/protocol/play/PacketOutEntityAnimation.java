package de.atlasmc.io.protocol.play;

import de.atlasmc.entity.Entity.Animation;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_ANIMATION)
public interface PacketOutEntityAnimation extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public Animation getAnimation();
	
	@Override
	default int getDefaultID() {
		return OUT_ENTITY_ANIMATION;
	}

}
