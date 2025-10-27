package de.atlasmc.node.world;

import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public class BiomeMusic implements NBTSerializable {
	
	public static final NBTCodec<BiomeMusic>
	NBT_HANDLER = NBTCodec
					.builder(BiomeMusic.class)
					.beginComponent("data")
					.enumStringOrType("sound", BiomeMusic::getSound, BiomeMusic::setSound, EnumSound.class, ResourceSound.NBT_CODEC)
					.intField("min_delay", BiomeMusic::getMinDelay, BiomeMusic::setMinDelay, 0)
					.intField("max_delay", BiomeMusic::getMaxDelay, BiomeMusic::setMaxDelay, 0)
					.boolField("replace_current_music", BiomeMusic::isRepalceCurrentMusic, BiomeMusic::setRepalceCurrentMusic, false)
					.endComponent()
					.intField("weigt", BiomeMusic::getWeight, BiomeMusic::setWeight, 0)
					.build();

	private Sound sound;
	private int minDelay;
	private int maxDelay;
	private boolean repalceCurrentMusic;
	private int weight;
	
	public Sound getSound() {
		return sound;
	}
	
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	public int getMinDelay() {
		return minDelay;
	}
	
	public void setMinDelay(int minDelay) {
		this.minDelay = minDelay;
	}
	
	public int getMaxDelay() {
		return maxDelay;
	}
	
	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}
	
	public boolean isRepalceCurrentMusic() {
		return repalceCurrentMusic;
	}
	
	public void setRepalceCurrentMusic(boolean repalceCurrentMusic) {
		this.repalceCurrentMusic = repalceCurrentMusic;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public NBTCodec<? extends BiomeMusic> getNBTCodec() {
		return NBT_HANDLER;
	}

}
