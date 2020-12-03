package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerPosition extends Packet {
	
	public double X();
	public double FeedY();
	public double Z();
	public boolean OnGround();

}
