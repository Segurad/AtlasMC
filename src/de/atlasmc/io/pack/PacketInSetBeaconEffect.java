package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInSetBeaconEffect extends Packet {
	
	public int PrimaryEffect();
	public int SecondaryEffect();

}
