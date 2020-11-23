package de.atlasmc;

import de.atlasmc.entity.Player;

public final class SoundConfiguration {

	private final SoundCategory category;
	private final String ssound;
	private final Sound sound;
	private final float volume, pitch;
	
	public SoundConfiguration(Sound sound, SoundCategory category, float volume, float pitch) {
		this.sound = sound; this.category = category; this.volume = volume; this.pitch = pitch;
		this.ssound = "";
	}
	
	public SoundConfiguration(Sound sound, SoundCategory category, float volume) {
		this(sound, category, volume, 1);
	}
	
	public SoundConfiguration(Sound sound, SoundCategory category) {
		this(sound, category, 1);
	}
	
	public SoundConfiguration(Sound sound) {
		this(sound, SoundCategory.MASTER);
	}
	
	public SoundConfiguration(String sound, SoundCategory category, float volume, float pitch) {
		this.ssound = sound; this.category = category; this.volume = volume; this.pitch = pitch;
		this.sound = null;
	}
	
	public SoundConfiguration(String sound, SoundCategory category, float volume) {
		this(sound, category, volume, 1);
	}
	
	public SoundConfiguration(String sound, SoundCategory category) {
		this(sound, category, 1);
	}
	
	public SoundConfiguration(String sound) {
		this(sound, SoundCategory.MASTER);
	}
	
	public void play(Location loc) {
		if (loc == null) return;
		if (sound != null) {
			loc.getWorld().playSound(loc, sound, category, volume, pitch);
		} else loc.getWorld().playSound(loc, ssound, category, volume, pitch);
	}
	
	public void play(Player player, Location loc) {
		if (player == null || loc == null) return;
		if (sound != null) {
			player.playSound(loc, sound, category, volume, pitch);
		} else player.playSound(loc, ssound, category, volume, pitch);
	}
	
	public void play(Player player) {
		play(player, player.getLocation());
	}
}
