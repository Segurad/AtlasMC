package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInUpdateJigsawBlock extends Packet {
	
	public long getPosition();
	public String getName();
	public String getTarget();
	public String getPool();
	public String getFinalState();
	public String getJointtype();

}
