package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SPAWN_EXPERIENCE_ORB)
public interface PacketOutSpawnExperienceOrb extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public void setEntityID(int id);
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public void setLocation(double x, double y, double z);
	
	public int getExperience();
	
	public void setExperience(int xp);
	
	@Override
	default int getDefaultID() {
		return OUT_SPAWN_EXPERIENCE_ORB;
	}

}
