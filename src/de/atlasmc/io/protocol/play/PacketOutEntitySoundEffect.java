package de.atlasmc.io.protocol.play;

import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_SOUND_EFFECT)
public interface PacketOutEntitySoundEffect extends PacketPlay, PacketOutbound {
	
	public Sound getSound();
	
	public SoundCategory getSoundCategory();
	
	public int getEntityID();
	
	public float getVolume();
	
	public float getPitch();
	
	public void setEntityID(int entityID);
	
	public void setCategory(SoundCategory category);
	
	public void setSound(Sound sound);
	
	public void setVolume(float volume);
	
	public void setPitch(float pitch);
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_SOUND_EFFECT;
	}

}
