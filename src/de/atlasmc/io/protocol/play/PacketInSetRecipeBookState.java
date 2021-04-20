package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInSetRecipeBookState extends Packet {
	
	public int getBookID();
	public boolean getBookOpen();
	public boolean getFilterActive();

}
