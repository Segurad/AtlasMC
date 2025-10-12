package de.atlasmc.node.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketPluginQuery;

@DefaultPacketID(packetID = PacketLogin.IN_LOGIN_PLUGIN_RESPONSE, definition = "custom_query_answer")
public class ServerboundLoginPluginResponse extends AbstractPacketPluginQuery implements PacketLoginServerbound {

	@Override
	public int getDefaultID() {
		return IN_LOGIN_PLUGIN_RESPONSE;
	}

}
