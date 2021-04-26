package de.atlasmc.io.protocol.login;

import de.atlasmc.io.Packet;

public interface PacketOutLoginPluginRequest extends Packet {
	
	public int getMessageID();
	public String getChannel();
	public byte[] getData();
	
	@Override
	public default int getDefaultID() {
		return 0x04;
	}

}
