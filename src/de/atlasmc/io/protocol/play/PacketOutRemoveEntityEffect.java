package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutRemoveEntityEffect extends Packet {
	
	public int getEntityID();
	public int getEffectID();
	
	@Override
	default int getDefaultID() {
		return 0x37;
	}

}
