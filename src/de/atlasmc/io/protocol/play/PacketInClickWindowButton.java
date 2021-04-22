package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInClickWindowButton extends Packet {

	public byte getWindowID();
	public byte getButtonID();
	
	@Override
	default int getDefaultID() {
		return 0x08;
	}
}
