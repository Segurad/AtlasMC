package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInCloseWindow extends Packet {
	
	public byte getWindowID();

}
