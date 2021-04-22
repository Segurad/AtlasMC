package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutWindowConfirmation extends Packet {
	
	public byte getWindowID();
	public short getActionNumber();
	public boolean isAccepted();
	
	@Override
	public default int getDefaultID() {
		return 0x11;
	}

}
