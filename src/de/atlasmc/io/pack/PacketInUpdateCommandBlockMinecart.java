package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInUpdateCommandBlockMinecart extends Packet {
	
	public int EntityID();
	public String Command();
	public boolean TrackOutput();

}
