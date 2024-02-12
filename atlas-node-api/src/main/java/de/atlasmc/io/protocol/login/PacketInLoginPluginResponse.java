package de.atlasmc.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketLogin.IN_LOGIN_PLUGIN_RESPONSE)
public class PacketInLoginPluginResponse extends AbstractPacket implements PacketLoginIn {
	
	public int messageID;
	public boolean successful;
	public byte[] data;
	
	@Override
	public int getDefaultID() {
		return IN_LOGIN_PLUGIN_RESPONSE;
	}

}
