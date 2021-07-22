package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ACKNOWLEDGE_PLAYER_DIGGIN)
public interface PacketOutAcknowledgePlayerDigging extends PacketPlay, PacketOutbound {
	
	public long getPosition();
	public int getBlockState();
	public boolean isSuccessful();
	
	@Override
	default int getDefaultID() {
		return OUT_ACKNOWLEDGE_PLAYER_DIGGIN;
	}

}
