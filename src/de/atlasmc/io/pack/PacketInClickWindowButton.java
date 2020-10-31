package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInClickWindowButton extends Packet {

	public byte getWindowID();
	public byte getButtonID();
}
