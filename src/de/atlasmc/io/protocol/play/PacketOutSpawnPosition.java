package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SPAWN_POSITION)
public interface PacketOutSpawnPosition extends PacketPlay, PacketOutbound {
	
	public long getPosition();
	
	@Override
	public default int getDefaultID() {
		return OUT_SPAWN_POSITION;
	}

}
