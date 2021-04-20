package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInSteerBoat extends Packet {
	
	public boolean getLeftPaddleTurning();
	public boolean getRightPaddleTurning();

}
