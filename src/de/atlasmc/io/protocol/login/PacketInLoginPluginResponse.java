package de.atlasmc.io.protocol.login;

import de.atlasmc.io.PacketInbound;

public interface PacketInLoginPluginResponse extends PacketLogin, PacketInbound {
	
	public int getMessageID();
	public boolean isSuccessful();
	public byte[] getData();
	
	@Override
	public default int getDefaultID() {
		return 0x02;
	}

}
