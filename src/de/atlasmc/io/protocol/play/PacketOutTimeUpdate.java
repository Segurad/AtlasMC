package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_TIME_UPDATE)
public interface PacketOutTimeUpdate extends PacketPlay, PacketOutbound {
	
	public long getWorldAge();
	public long getTimeOfDay();
	
	@Override
	public default int getDefaultID() {
		return OUT_TIME_UPDATE;
	}

}
