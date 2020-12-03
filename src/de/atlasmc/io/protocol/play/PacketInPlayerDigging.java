package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInPlayerDigging extends Packet {
	
	public int Status();
	public SimpleLocation Position();
	public byte Face();
	
}
