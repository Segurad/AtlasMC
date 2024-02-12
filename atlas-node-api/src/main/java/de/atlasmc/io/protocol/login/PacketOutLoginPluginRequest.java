package de.atlasmc.io.protocol.login;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketLogin.OUT_LOGIN_PLUGIN_REQUEST)
public class PacketOutLoginPluginRequest extends AbstractPacket implements PacketLoginOut {
	
	public int messageID;
	public NamespacedKey channel;
	public byte[] data;
	
	@Override
	public int getDefaultID() {
		return OUT_LOGIN_PLUGIN_REQUEST;
	}

}
