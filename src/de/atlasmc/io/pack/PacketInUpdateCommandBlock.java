package de.atlasmc.io.pack;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInUpdateCommandBlock extends Packet {
	
	public SimpleLocation Position();
	public String Command();
	public int Mode();
	public byte Flags();

}
