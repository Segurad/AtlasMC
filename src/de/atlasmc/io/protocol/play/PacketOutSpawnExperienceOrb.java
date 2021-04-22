package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutSpawnExperienceOrb extends Packet {
	
	public int getEntityID();
	public double getX();
	public double getY();
	public double getZ();
	public int getExperience();
	
	@Override
	default int getDefaultID() {
		return 0x01;
	}

}
