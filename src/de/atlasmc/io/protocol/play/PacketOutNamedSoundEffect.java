package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.Packet;

public interface PacketOutNamedSoundEffect extends Packet {
	
	public String getIdentifier();
	public SoundCategory getCategory();
	public double getX();
	public double getY();
	public double getZ();
	public float getVolume();
	public float getPitch();
	
	@Override
	default int getDefaultID() {
		return 0x18;
	}

}
