package de.atlasmc.node.io.protocol.login;

import java.util.UUID;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketLogin.IN_LOGIN_START, definition = "hello")
public class ServerboundLoginStart extends AbstractPacket implements PacketLoginServerbound {
	
	public String name;
	public UUID uuid;
	
	@Override
	public int getDefaultID() {
		return IN_LOGIN_START;
	}

}
