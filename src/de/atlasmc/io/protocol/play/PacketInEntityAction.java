package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInEntityAction extends Packet {
	
	public int EntityID();
	public int ActionID();
	public int JumpBoost();

}