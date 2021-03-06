package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_STOP_SOUND)
public interface PacketOutStopSound extends PacketPlay, PacketOutbound {
	
	public SoundCategory getCategory();
	
	public String getSound();
	
	public void setCategory(SoundCategory category);

	public void setSound(String sound);
	
	@Override
	public default int getDefaultID() {
		return OUT_STOP_SOUND;
	}
}
