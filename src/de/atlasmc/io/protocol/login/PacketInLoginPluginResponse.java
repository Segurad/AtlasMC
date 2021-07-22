package de.atlasmc.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketLogin.IN_LOGIN_PLUGIN_RESPONSE)
public interface PacketInLoginPluginResponse extends PacketLogin, PacketInbound {
	
	public int getMessageID();
	public boolean isSuccessful();
	public byte[] getData();
	
	@Override
	public default int getDefaultID() {
		return IN_LOGIN_PLUGIN_RESPONSE;
	}

}
