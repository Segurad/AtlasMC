package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.Packet;

public interface PacketOutSoundEffect extends Packet {
	
	public int getSoundID();
	public SoundCategory getCategory();
	public double getX();
	public double getY();
	public double getZ();
	public float getValue();
	public float getPitch();
	
	@Override
	public default int getDefaultID() {
		return 0x51;
	}

}
