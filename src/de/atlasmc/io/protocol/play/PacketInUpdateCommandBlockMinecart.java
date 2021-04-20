package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInUpdateCommandBlockMinecart extends Packet {
	
	public int getEntityID();
	public String getCommand();
	public boolean getTrackOutput();

}
