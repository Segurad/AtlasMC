package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_KEEP_ALIVE)
public interface PacketInKeepAlive extends PacketPlay, PacketInbound {
	
	public long getKeepAliveID();

	@Override
	default int getDefaultID() {
		return IN_KEEP_ALIVE;
	}
	
}
