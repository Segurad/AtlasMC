package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInEntityAction extends Packet {
	
	public int getEntityID();
	public int getActionID();
	public int getJumpBoost();
	
	@Override
	default int getDefaultID() {
		return 0x1C;
	}

}
