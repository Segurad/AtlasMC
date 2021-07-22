package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_UPDATE_LIGHT)
public interface PacketOutUpdateLight extends PacketPlay, PacketOutbound {
	
	@Override
	default int getDefaultID() {
		return OUT_UPDATE_LIGHT;
	}

}
