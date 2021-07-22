package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_PLAYER_BLOCK_PLACEMENT)
public interface PacketInPlayerBlockPlacement extends PacketPlay, PacketInbound {
	
	public int getHand();
	public long getPosition();
	public int getFace();
	public float getCursurPositionX();
	public float getCursurPositionY();
	public float getCursurPositionZ();
	public boolean isInsideblock();
	
	@Override
	default int getDefaultID() {
		return IN_PLAYER_BLOCK_PLACEMENT;
	}

}
