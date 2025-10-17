package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface PlaySound extends ComponentEffect {
	
	public static final NBTCodec<PlaySound>
	NBT_HANDLER = NBTCodec
					.builder(PlaySound.class)
					.include(ComponentEffect.NBT_HANDLER)
					.addField(Sound.getNBTSoundField("sound", PlaySound::getSound, PlaySound::setSound, null))
					.build();
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	PlaySound clone();
	
	@Override
	default NBTCodec<? extends PlaySound> getNBTCodec() {
		return NBT_HANDLER;
	}

}
