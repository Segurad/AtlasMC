package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInWindowConfirmation extends Packet {

	public byte getWindowID();
	public short getActionNumber();
	public boolean getAccepted();
	
}
