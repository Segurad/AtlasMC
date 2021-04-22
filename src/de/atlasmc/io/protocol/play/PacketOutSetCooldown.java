package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutSetCooldown extends Packet {
	
	public int getItemID();
	public int getCooldown();
	
	@Override
	default int getDefaultID() {
		return 0x16;
	}

}
