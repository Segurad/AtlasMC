package de.atlasmc.io.protocol.play;

import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SOUND_EFFECT)
public class PacketOutSoundEffect extends AbstractPacket implements PacketPlayOut {
	
	private Sound sound;
	private SoundCategory category;
	private double x, y, z;
	private float volume, pitch;
	private float range;
	private boolean fixedRange;
	private String identifier;
	private long seed;
	
	public long getSeed() {
		return seed;
	}
	
	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public float getRange() {
		return range;
	}
	
	public void setRange(float range) {
		this.range = range;
	}
	
	public boolean hasFixedRange() {
		return fixedRange;
	}
	
	public void setFixedRange(boolean fixedRange) {
		this.fixedRange = fixedRange;
	}
	
	public Sound getSound() {
		return sound;
	}

	public SoundCategory getCategory() {
		return category;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getVolume() {
		return volume;
	}

	public float getPitch() {
		return pitch;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public void setCategory(SoundCategory category) {
		this.category = category;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SOUND_EFFECT;
	}

}
