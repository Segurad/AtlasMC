package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_KEEP_ALIVE)
public interface PacketOutKeepAlive extends PacketPlay, PacketOutbound {

	public long getKeepAlive();
	
	@Override
	public default int getDefaultID() {
		return OUT_KEEP_ALIVE;
	}
	
}
