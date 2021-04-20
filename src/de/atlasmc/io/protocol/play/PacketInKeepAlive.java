package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInKeepAlive extends Packet {
	
	public long getKeepAliveID();

}
