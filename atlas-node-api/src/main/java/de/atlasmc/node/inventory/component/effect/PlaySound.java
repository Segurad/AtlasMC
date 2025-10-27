package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface PlaySound extends ComponentEffect {
	
	public static final NBTCodec<PlaySound>
	NBT_CODEC = NBTCodec
					.builder(PlaySound.class)
					.include(ComponentEffect.NBT_HANDLER)
					.enumStringOrType("sound", PlaySound::getSound, PlaySound::setSound, EnumSound.class, ResourceSound.NBT_CODEC)
					.build();
	
	public static final StreamCodec<PlaySound>
	STREAM_CODEC = StreamCodec
					.builder(PlaySound.class)
					.include(ComponentEffect.STREAM_CODEC)
					.enumValueOrCodec(PlaySound::getSound, PlaySound::setSound, EnumSound.class, ResourceSound.STREAM_CODEC)
					.build();
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	PlaySound clone();
	
	@Override
	default NBTCodec<? extends PlaySound> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends ComponentEffect> getStreamCodec() {
		return STREAM_CODEC;
	}

}
