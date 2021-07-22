package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_UPDATE_VIEW_DISTANCE)
public interface PacketOutUpdateViewDistance extends PacketPlay, PacketOutbound {
	
	public int getDistance();
	
	@Override
	public default int getDefaultID() {
		return OUT_UPDATE_VIEW_DISTANCE;	
	}

}
