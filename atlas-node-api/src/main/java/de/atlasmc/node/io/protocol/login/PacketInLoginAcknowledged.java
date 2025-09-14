package de.atlasmc.node.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketLogin.IN_LOGIN_ACKNOWLEDGED, definition = "login_acknowledged")
public class PacketInLoginAcknowledged extends AbstractPacket implements PacketLoginIn {

	@Override
	public int getDefaultID() {
		return IN_LOGIN_ACKNOWLEDGED;
	}

}
