package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerBlockPlacement extends Packet {
	
	public int getHand();
	public long getPosition();
	public int getFace();
	public float getCursurPositionX();
	public float getCursurPositionY();
	public float getCursurPositionZ();
	public boolean isInsideblock();

}
