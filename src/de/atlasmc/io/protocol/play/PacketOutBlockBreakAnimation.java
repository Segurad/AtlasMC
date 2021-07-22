package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_BLOCK_BREAK_ANIMATION)
public interface PacketOutBlockBreakAnimation extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public long getPosition();
	public int getStage();
	
	@Override
	default int getDefaultID() {
		return OUT_BLOCK_BREAK_ANIMATION;
	}

}
