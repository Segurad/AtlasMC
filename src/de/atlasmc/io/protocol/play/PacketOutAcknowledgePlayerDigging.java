package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutAcknowledgePlayerDigging extends Packet {
	
	public long getPosition();
	public int getBlockState();
	public boolean isSuccessful();
	
	@Override
	default int getDefaultID() {
		return 0x07;
	}

}
