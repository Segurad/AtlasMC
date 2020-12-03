package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInUpdateCommandBlock extends Packet {
	
	public SimpleLocation Position();
	public String Command();
	public int Mode();
	public byte Flags();

}
