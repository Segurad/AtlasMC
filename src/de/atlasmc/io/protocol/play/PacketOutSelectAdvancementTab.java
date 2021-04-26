package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutSelectAdvancementTab extends Packet {
	
	public boolean hasTabID();
	public String getTabID();

	@Override
	public default int getDefaultID() {
		return 0x3C;
	}

}
