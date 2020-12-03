package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPluginMessage extends Packet {
	
	public String getChannel();
	public byte[] getData();

}
