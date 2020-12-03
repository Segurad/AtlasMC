package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInPlayerBlockPlacement extends Packet {
	
	public int Hand();
	public SimpleLocation Position();
	public int Face();
	public float CursurPositionX();
	public float CursurPositionY();
	public float CursurPositionZ();
	public boolean Insideblock();

}
