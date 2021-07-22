package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_UPDATE_HEALTH)
public interface PacketOutUpdateHealth extends PacketPlay, PacketOutbound {
	
	public float getHealth();
	public int getFood();
	public float getSaturation();
	
	@Override
	public default int getDefaultID() {
		return OUT_UPDATE_HEALTH;
	}

}
