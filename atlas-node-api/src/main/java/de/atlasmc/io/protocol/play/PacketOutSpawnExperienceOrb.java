package de.atlasmc.io.protocol.play;

import de.atlasmc.entity.ExperienceOrb;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SPAWN_EXPERIENCE_ORB)
public class PacketOutSpawnExperienceOrb extends AbstractPacket implements PacketPlayOut {
	
	private int entityID, xp;
	private double x, y, z;
	
	public int getEntityID() {
		return entityID;
	}

	public int getExperience() {
		return xp;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public void setExperience(int xp) {
		this.xp = xp;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setEntity(ExperienceOrb orb) {
		entityID = orb.getID();
		x = orb.getX();
		y = orb.getY();
		z = orb.getZ();
		xp = orb.getExperience();
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SPAWN_EXPERIENCE_ORB;
	}

}
