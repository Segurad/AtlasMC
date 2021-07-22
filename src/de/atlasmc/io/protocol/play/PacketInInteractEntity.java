package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_INTERACT_ENTITY)
public interface PacketInInteractEntity extends PacketPlay, PacketInbound {
	
	public int getEntityID();
	public int getType();
	public float getX();
	public float getY();
	public float getZ();
	public int getHand();
	public boolean Sneaking();
	
	@Override
	default int getDefaultID() {
		return IN_INTERACT_ENTITY;
	}
	
}
