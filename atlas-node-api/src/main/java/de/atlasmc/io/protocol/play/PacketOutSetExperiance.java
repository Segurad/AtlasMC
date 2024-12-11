package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_EXPERIENCE, definition = "set_experience")
public class PacketOutSetExperiance extends AbstractPacket implements PacketPlayOut {
	
	public int level;
	public int totalExperience;
	public float experienceBar;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_EXPERIENCE;
	}

}
