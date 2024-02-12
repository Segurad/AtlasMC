package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_STOP_SOUND)
public class PacketOutStopSound extends AbstractPacket implements PacketPlayOut {
	
	private SoundCategory category;
	private String sound;
	
	public SoundCategory getCategory() {
		return category;
	}
	
	public void setCategory(SoundCategory category) {
		this.category = category;
	}
	
	public String getSound() {
		return sound;
	}
	
	public void setSound(String sound) {
		this.sound = sound;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_STOP_SOUND;
	}
}
