package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInGenerateStructure extends Packet {
	
	public SimpleLocation Position();
	public int Levels();
	public boolean Keep_Jigsaws();

}
