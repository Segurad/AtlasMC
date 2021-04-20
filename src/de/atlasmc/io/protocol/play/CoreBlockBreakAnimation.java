package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface CoreBlockBreakAnimation extends Packet {
	
	public int getEntityID();
	public long getPosition();
	public int getStage();

}
