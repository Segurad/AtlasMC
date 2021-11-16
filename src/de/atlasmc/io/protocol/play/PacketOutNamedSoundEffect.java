package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_NAMED_SOUD_EFFECT)
public interface PacketOutNamedSoundEffect extends PacketPlay, PacketOutbound {
	
	public String getIdentifier();
	
	public SoundCategory getCategory();
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public float getVolume();
	
	public float getPitch();
	
	public void setPosition(double x, double y, double z);
	
	public void setCategory(SoundCategory category);
	
	public void setIdentifier(String sound);
	
	public void setVolume(float volume);
	
	public void setPitch(float pitch);
	
	@Override
	default int getDefaultID() {
		return OUT_NAMED_SOUD_EFFECT;
	}

}
