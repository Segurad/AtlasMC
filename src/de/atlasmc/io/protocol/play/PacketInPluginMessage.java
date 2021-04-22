package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPluginMessage extends Packet {
	
	public String getChannel();
	public byte[] getData();
	
	@Override
	default int getDefaultID() {
		return 0x0B;
	}

}
