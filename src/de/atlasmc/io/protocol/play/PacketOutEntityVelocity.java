package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutEntityVelocity extends Packet {
	
	public int getEntityID();
	public short getVelocityX();
	public short getVelocityY();
	public short getVelocityZ();
	
	@Override
	public default int getDefaultID() {
		return 0x46;
	}

}
