package de.atlasmc.io.protocol.play;

import de.atlasmc.SoundCategory;
import de.atlasmc.io.Packet;

public interface PacketOutStopSound extends Packet {
	
	public SoundCategory getCategory();
	public String getSound();
	
	@Override
	public default int getDefaultID() {
		return 0x52;
	}

}
