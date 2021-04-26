package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutSetExperiance extends Packet {
	
	public float getExperienceBar();
	public int getLevel();
	public int getTotalExperience();
	
	@Override
	public default int getDefaultID() {
		return 0x48;
	}

}
