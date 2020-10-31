package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInCloseWindow extends Packet {
	
	public byte getWindowID();

}
