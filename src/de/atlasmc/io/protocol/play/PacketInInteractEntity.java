package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInInteractEntity extends Packet {
	
	public int getEntityID();
	public int getType();
	public float getX();
	public float getY();
	public float getZ();
	public int getHand();
	public boolean Sneaking();
	
	
	

}
