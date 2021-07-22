package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_CAMERA)
public interface PacketOutCamera extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	@Override
	public default int getDefaultID() {
		return OUT_CAMERA;
	}

}
