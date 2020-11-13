package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInSetRecipeBookState extends Packet {
	
	public int BookID();
	public boolean BookOpen();
	public boolean FilterActive();

}
