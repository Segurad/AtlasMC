package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerPosition extends Packet {
	
	public double getX();
	public double getFeedY();
	public double getZ();
	public boolean isOnGround();
	
	@Override
	default int getDefaultID() {
		return 0x12;
	}

}
