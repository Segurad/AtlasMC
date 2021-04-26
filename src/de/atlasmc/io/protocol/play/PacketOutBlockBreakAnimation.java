package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutBlockBreakAnimation extends Packet {
	
	public int getEntityID();
	public long getPosition();
	public int getStage();
	
	@Override
	default int getDefaultID() {
		return 0x08;
	}

}
