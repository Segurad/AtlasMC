package de.atlasmc.sound;

import de.atlasmc.SoundCategory;

public interface SoundEmitter {
	
	void causeSound(Sound sound, SoundCategory category, float volume, float pitch, long seed);
	
	default void causeSound(Sound sound, SoundCategory category, float volume, float pitch) {
		causeSound(sound, category, volume, pitch, Sound.DEFAULT_SEED);
	}
	
	default void causeSound(Sound sound, float volume, float pitch) {
		causeSound(sound, SoundCategory.MASTER, volume, pitch, Sound.DEFAULT_SEED);
	}
	
	default void causeSound(Sound sound) {
		causeSound(sound, SoundCategory.MASTER, 0.5f, 1.0f, Sound.DEFAULT_SEED);
	}
	
	default void causeSound(Sound sound, float volume, float pitch, long seed) {
		causeSound(sound, SoundCategory.MASTER, volume, pitch, seed);
	}

}
