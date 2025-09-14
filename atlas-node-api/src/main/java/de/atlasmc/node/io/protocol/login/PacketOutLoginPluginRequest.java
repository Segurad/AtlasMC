package de.atlasmc.node.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketPluginQuery;

@DefaultPacketID(packetID = PacketLogin.OUT_LOGIN_PLUGIN_REQUEST, definition = "custom_query")
public class PacketOutLoginPluginRequest extends AbstractPacketPluginQuery implements PacketLoginOut {
	
	@Override
	public int getDefaultID() {
		return OUT_LOGIN_PLUGIN_REQUEST;
	}

}
