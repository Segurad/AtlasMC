package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutBlockChange extends Packet {
	
	public long getPosition();
	public int getBlockStateID();

}
