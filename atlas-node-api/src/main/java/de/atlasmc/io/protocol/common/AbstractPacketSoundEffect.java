package de.atlasmc.io.protocol.common;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.sound.Sound;

public abstract class AbstractPacketSoundEffect extends AbstractPacket {
	
	public Sound sound;
	public SoundCategory category;
	public float volume;
	public float pitch;
	public long seed;

}
