package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInUpdateSign extends Packet {
	
	public SimpleLocation Position();
	public String Line1();
	public String Line2();
	public String Line3();
	public String Line4();

}
