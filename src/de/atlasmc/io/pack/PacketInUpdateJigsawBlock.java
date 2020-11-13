package de.atlasmc.io.pack;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInUpdateJigsawBlock extends Packet {
	
	public SimpleLocation Position();
	public String Name();
	public String Target();
	public String Pool();
	public String FinalState();
	public String Jointtype();

}
