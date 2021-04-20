package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutEntityStatus extends Packet {
	
	public int getEntityID();
	public int getStatus();

}
