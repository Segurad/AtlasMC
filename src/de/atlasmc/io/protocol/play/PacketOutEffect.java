package de.atlasmc.io.protocol.play;

import de.atlasmc.Effect;
import de.atlasmc.io.Packet;

public interface PacketOutEffect extends Packet {
	
	public Effect getEffect();
	public long getPosition();
	public int getData();
	public boolean getDisableRelativVolume();
	
	@Override
	default int getDefaultID() {
		return 0x21;
	}

}
