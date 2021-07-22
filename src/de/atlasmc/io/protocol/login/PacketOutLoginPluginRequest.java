package de.atlasmc.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketLogin.OUT_LOGIN_PLUGIN_REQUEST)
public interface PacketOutLoginPluginRequest extends PacketLogin, PacketOutbound {
	
	public int getMessageID();
	public String getChannel();
	public byte[] getData();
	
	@Override
	public default int getDefaultID() {
		return OUT_LOGIN_PLUGIN_REQUEST;
	}

}
