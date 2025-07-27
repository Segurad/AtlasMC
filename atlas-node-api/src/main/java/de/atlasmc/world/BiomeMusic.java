package de.atlasmc.world;

import de.atlasmc.sound.Sound;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class BiomeMusic implements NBTSerializable {
	
	public static final NBTSerializationHandler<BiomeMusic>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BiomeMusic.class)
					.beginComponent("data")
					.addField(Sound.getNBTSoundField("sound", BiomeMusic::getSound, BiomeMusic::setSound, null))
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
	public NBTSerializationHandler<? extends BiomeMusic> getNBTHandler() {
		return NBT_HANDLER;
	}

}
