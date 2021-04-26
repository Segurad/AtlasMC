package de.atlasmc.io.protocol.login;

import de.atlasmc.io.Packet;

public interface PacketInLoginPluginResponse extends Packet {
	
	public int getMessageID();
	public boolean isSuccessful();
	public byte[] getData();
	
	@Override
	public default int getDefaultID() {
		return 0x02;
	}

}
