package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_HEAD_LOOK)
public interface PacketOutEntityHeadLook extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public float getYaw();
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_HEAD_LOOK;
	}

}
