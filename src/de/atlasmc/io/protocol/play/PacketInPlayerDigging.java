package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerDigging extends Packet {
	
	public int getStatus();
	public long getPosition();
	public byte getFace();
	
}
