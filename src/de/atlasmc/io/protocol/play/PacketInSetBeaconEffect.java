package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInSetBeaconEffect extends Packet {
	
	public int getPrimaryEffect();
	public int getSecondaryEffect();
	
	@Override
	default int getDefaultID() {
		return 0x24;
	}

}
