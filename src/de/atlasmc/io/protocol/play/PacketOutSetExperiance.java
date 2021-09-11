package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SET_EXPERIENCE)
public interface PacketOutSetExperiance extends PacketPlay, PacketOutbound {
	
	public float getExperienceBar();
	
	public int getLevel();
	
	public int getTotalExperience();
	
	@Override
	public default int getDefaultID() {
		return OUT_SET_EXPERIENCE;
	}
	
	public void setLevel(int level);
	
	public void setTotalExperience(int total);
	
	/**
	 * 
	 * @param bar value between 0 and 1
	 */
	public void setExperienceBar(float bar);

}
