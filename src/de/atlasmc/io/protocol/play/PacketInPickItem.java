package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPickItem extends Packet {
	
	public int getSlotToUse();

}
