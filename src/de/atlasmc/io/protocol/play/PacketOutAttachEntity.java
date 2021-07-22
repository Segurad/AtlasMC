package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ATTACH_ENTITY)
public interface PacketOutAttachEntity extends PacketPlay, PacketOutbound {
	
	public int getAttachedEntityID();
	public int getHoldingEntityID();
	
	@Override
	public default int getDefaultID() {
		return OUT_ATTACH_ENTITY;
	}

}
