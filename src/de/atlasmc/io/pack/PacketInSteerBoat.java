package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInSteerBoat extends Packet {
	
	public boolean LeftPaddleTurning();
	public boolean RightPaddleTurning();

}
