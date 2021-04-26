package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.Packet;

public interface PacketOutEntitySoundEffect extends Packet {
	
	public int getSoundID();
	public SoundCategory getSoundCategory();
	public int getEntityID();
	public float getVolume();
	public float getPitch();
	
	@Override
	public default int getDefaultID() {
		return 0x50;
	}

}
