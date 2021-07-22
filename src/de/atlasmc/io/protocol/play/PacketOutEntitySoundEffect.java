package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_SOUND_EFFECT)
public interface PacketOutEntitySoundEffect extends PacketPlay, PacketOutbound {
	
	public int getSoundID();
	public SoundCategory getSoundCategory();
	public int getEntityID();
	public float getVolume();
	public float getPitch();
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_SOUND_EFFECT;
	}

}
