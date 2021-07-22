package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SPAWN_EXPERIENCE_ORB)
public interface PacketOutSpawnExperienceOrb extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public double getX();
	public double getY();
	public double getZ();
	public int getExperience();
	
	@Override
	default int getDefaultID() {
		return OUT_SPAWN_EXPERIENCE_ORB;
	}

}
