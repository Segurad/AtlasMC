package de.atlasmc.node.io.protocol.common;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.node.SoundCategory;
import de.atlasmc.node.sound.Sound;

public abstract class AbstractPacketSoundEffect extends AbstractPacket {
	
	public Sound sound;
	public SoundCategory category;
	public float volume;
	public float pitch;
	public long seed;

}
