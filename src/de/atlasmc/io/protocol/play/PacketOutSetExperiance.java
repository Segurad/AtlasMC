package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_EXPERIENCE)
public class PacketOutSetExperiance extends AbstractPacket implements PacketPlayOut {
	
	private int level, totalExperience;
	private float experienceBar;
	
	/**
	 * 
	 * @param bar value between 0 and 1
	 */
	public void setExperienceBar(float bar) {
		this.experienceBar = bar;
	}
	
	public int getLevel() {
		return level;
	}

	public int getTotalExperience() {
		return totalExperience;
	}

	public float getExperienceBar() {
		return experienceBar;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setTotalExperience(int totalExperience) {
		this.totalExperience = totalExperience;
	}

	@Override
	public int getDefaultID() {
		return OUT_SET_EXPERIENCE;
	}

}
