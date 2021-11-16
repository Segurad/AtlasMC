package de.atlasmc.io.protocol.play;

import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SOUND_EFFECT)
public interface PacketOutSoundEffect extends PacketPlay, PacketOutbound {
	
	public Sound getSound();
	
	public SoundCategory getCategory();
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public float getVolume();
	
	public float getPitch();
	
	public void setPosition(double x, double y, double z);
	
	public void setCategory(SoundCategory category);
	
	public void setSound(Sound sound);
	
	public void setVolume(float volume);
	
	public void setPitch(float pitch);
	
	@Override
	public default int getDefaultID() {
		return OUT_SOUND_EFFECT;
	}

}
