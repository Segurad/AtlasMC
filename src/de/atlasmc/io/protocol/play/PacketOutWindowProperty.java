package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutWindowProperty extends Packet {

	public byte getWindowID();
	public int getProperty();
	public int getValue();
	
}
