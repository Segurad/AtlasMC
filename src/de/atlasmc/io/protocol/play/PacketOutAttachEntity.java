package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutAttachEntity extends Packet {
	
	public int getAttachedEntityID();
	public int getHoldingEntityID();
	
	@Override
	public default int getDefaultID() {
		return 0x45;
	}

}
