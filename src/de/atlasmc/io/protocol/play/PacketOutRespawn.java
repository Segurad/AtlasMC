package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_RESPAWN)
public interface PacketOutRespawn extends PacketPlay, PacketOutbound {
	
	@Override
	default int getDefaultID() {
		return OUT_RESPAWN;
	}

}
