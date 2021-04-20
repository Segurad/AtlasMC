package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInHeldItemChange extends Packet {
	
	public short getSlot();

}
