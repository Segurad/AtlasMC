package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutBlockAction extends Packet {
	
	public long getPosition();
	public int getActionID();
	public int getActionParam();
	public int getBlockType();
	
	@Override
	default int getDefaultID() {
		return 0x0A;
	}

}
