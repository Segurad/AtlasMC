package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInClientStatus extends Packet {
	
	public int getActionID();

}