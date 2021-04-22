package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutPluginMessage extends Packet {
	
	public String getIdentifier();
	public byte[] getData();
	
	@Override
	public default int getDefaultID() {
		return 0x17;
	}

}
