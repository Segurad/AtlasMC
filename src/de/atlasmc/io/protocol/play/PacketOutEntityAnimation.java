package de.atlasmc.io.protocol.play;

import de.atlasmc.entity.Entity.Animation;

public interface PacketOutEntityAnimation {
	
	public int getEntityID();
	public Animation getAnimation();

}
