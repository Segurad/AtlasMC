package de.atlasmc.node.sound;

import org.joml.Vector3d;

import de.atlasmc.node.SoundCategory;
import de.atlasmc.node.entity.Entity;

public interface SoundListener {

	// Entity sound
	
	default void playSound(Entity entity, Sound sound, float volume, float pitch) {
		playSound(entity, sound, SoundCategory.MASTER, volume, pitch, Sound.DEFAULT_SEED);
	}
	
	default void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch) {
		playSound(entity, sound, category, volume, pitch, Sound.DEFAULT_SEED);
	}
	
	default void playSound(Entity entity, Sound sound, float volume, float pitch, long seed) {
		playSound(entity, sound, SoundCategory.MASTER, volume, pitch, seed);
	}
	
	void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch, long seed);
	
	// Located sound
	
	default void playSound(Vector3d loc, Sound sound, float volume, float pitch) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playSound(loc.x, loc.y, loc.z, sound, volume, pitch, Sound.DEFAULT_SEED);
	}
	
	default void playSound(Vector3d loc, Sound sound, SoundCategory category, float volume, float pitch) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playSound(loc.x, loc.y, loc.z, sound, category, volume, pitch, Sound.DEFAULT_SEED);
	}
	
	default void playSound(double x, double y, double z, Sound sound, float volume, float pitch) {
		playSound(x, y, z, sound, SoundCategory.MASTER, volume, pitch, Sound.DEFAULT_SEED);
	}
	
	default void playSound(Vector3d loc, Sound sound, float volume, float pitch, long seed) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playSound(loc.x, loc.y, loc.z, sound, volume, pitch, seed);
	}
	
	default void playSound(Vector3d loc, Sound sound, SoundCategory category, float volume, float pitch, long seed) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playSound(loc.x, loc.y, loc.z, sound, category, volume, pitch, seed);
	}
	
	default void playSound(double x, double y, double z, Sound sound, float volume, float pitch, long seed) {
		playSound(x, y, z, sound, SoundCategory.MASTER, volume, pitch, seed);
	}
	
	void playSound(double x, double y, double z, Sound sound, SoundCategory category, float volume, float pitch, long seed);
	
}
