package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_NAMED_SOUD_EFFECT)
public class PacketOutNamedSoundEffect extends AbstractPacket implements PacketPlayOut {
	
	private String identifier;
	private SoundCategory category;
	private double x, y, z;
	private float volume, pitch;
	
	public String getIdentifier() {
		return identifier;
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
	
	public void setLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getVolume() {
		return volume;
	}

	public float getPitch() {
		return pitch;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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

	@Override
	public int getDefaultID() {
		return OUT_NAMED_SOUD_EFFECT;
	}

}
