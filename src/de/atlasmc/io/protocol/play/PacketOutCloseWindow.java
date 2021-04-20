package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutCloseWindow extends Packet {
	
	public byte getWindowID();

}
