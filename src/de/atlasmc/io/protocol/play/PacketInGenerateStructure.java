package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInGenerateStructure extends Packet {
	
	public long getPosition();
	public int getLevels();
	public boolean getKeepJigsaws();
	
	@Override
	default int getDefaultID() {
		return 0x0F;
	}

}
