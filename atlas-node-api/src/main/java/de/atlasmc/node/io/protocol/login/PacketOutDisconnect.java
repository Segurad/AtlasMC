package de.atlasmc.node.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketDisconnect;

@DefaultPacketID(packetID = PacketLogin.OUT_DISCONNECT, definition = "login_disconnect")
public class PacketOutDisconnect extends AbstractPacketDisconnect implements PacketLoginOut {
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
