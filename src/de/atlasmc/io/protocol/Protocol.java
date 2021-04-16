package de.atlasmc.io.protocol;

import de.atlasmc.io.Packet;

public interface Protocol {
	
	public Packet createPacket(int id);

}
