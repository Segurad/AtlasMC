package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutOpenHorseWindow extends Packet {
	
	public byte getWindowID();
	public int getSlots();
	public int getEntityID();
	
	@Override
	default int getDefaultID() {
		return 0x1E;
	}

}
