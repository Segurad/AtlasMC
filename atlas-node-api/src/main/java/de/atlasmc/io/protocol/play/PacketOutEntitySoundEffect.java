package de.atlasmc.io.protocol.play;

import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_ENTITY_SOUND_EFFECT)
public class PacketOutEntitySoundEffect extends AbstractPacket implements PacketPlayOut {
	
	private Sound sound;
	private String identifier;
	private boolean fixedRange;
	private float range;
	private SoundCategory category;
	private int entityID;
	private float volume;
	private float pitch;
	private long seed;
	
	public Sound getSound() {
		return sound;
	}
	
	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	public long getSeed() {
		return seed;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public boolean hasFixedRange() {
		return fixedRange;
	}
	
	public void setFixedRange(boolean fixedRange) {
		this.fixedRange = fixedRange;
	}
	
	public float getRange() {
		return range;
	}
	
	public void setRange(float range) {
		this.range = range;
	}
	
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	public SoundCategory getCategory() {
		return category;
	}
	
	public void setCategory(SoundCategory category) {
		this.category = category;
	}
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_SOUND_EFFECT;
	}

}
