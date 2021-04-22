package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInSteerBoat extends Packet {
	
	public boolean getLeftPaddleTurning();
	public boolean getRightPaddleTurning();
	
	@Override
	default int getDefaultID() {
		return 0x17;
	}

}
