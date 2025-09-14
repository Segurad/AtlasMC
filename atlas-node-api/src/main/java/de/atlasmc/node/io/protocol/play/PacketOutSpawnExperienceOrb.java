package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.entity.ExperienceOrb;

@DefaultPacketID(packetID = PacketPlay.OUT_SPAWN_EXPERIENCE_ORB, definition = "add_experience_orb")
public class PacketOutSpawnExperienceOrb extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public double x;
	public double y;
	public double z;
	public int count;
	
	public void setEntity(ExperienceOrb orb) {
		entityID = orb.getID();
		x = orb.getX();
		y = orb.getY();
		z = orb.getZ();
		count = orb.getExperience();
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SPAWN_EXPERIENCE_ORB;
	}

}
