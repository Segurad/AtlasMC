package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInInteractEntity extends Packet {
	
	public int EntityID();
	public int Type();
	public float X();
	public float Y();
	public float Z();
	public int Hand();
	public boolean Sneaking();
	
	
	

}
