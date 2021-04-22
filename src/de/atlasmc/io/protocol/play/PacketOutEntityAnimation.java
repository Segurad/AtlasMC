package de.atlasmc.io.protocol.play;

import de.atlasmc.entity.Entity.Animation;
import de.atlasmc.io.Packet;

public interface PacketOutEntityAnimation extends Packet {
	
	public int getEntityID();
	public Animation getAnimation();
	
	@Override
	default int getDefaultID() {
		return 0x05;
	}

}
