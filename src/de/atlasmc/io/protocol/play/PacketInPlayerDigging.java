package de.atlasmc.io.protocol.play;

import de.atlasmc.block.BlockFace;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_PLAYER_DIGGING)
public interface PacketInPlayerDigging extends PacketPlay, PacketInbound {
	
	public int getStatus();
	public long getPosition();
	public BlockFace getFace();
	
	@Override
	default int getDefaultID() {
		return IN_PLAYER_DIGGING;
	}
	
}
