package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInUpdateSign extends Packet {
	
	public long getPosition();
	public String getLine1();
	public String getLine2();
	public String getLine3();
	public String getLine4();

}
