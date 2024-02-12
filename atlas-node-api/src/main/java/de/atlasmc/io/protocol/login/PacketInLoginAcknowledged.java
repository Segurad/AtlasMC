package de.atlasmc.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketLogin.IN_LOGIN_ACKNOWLEDGED)
public class PacketInLoginAcknowledged extends AbstractPacket implements PacketLoginIn {

	@Override
	public int getDefaultID() {
		return IN_LOGIN_ACKNOWLEDGED;
	}

}
