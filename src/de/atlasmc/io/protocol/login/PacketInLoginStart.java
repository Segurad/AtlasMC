package de.atlasmc.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketLogin.IN_LOGIN_START)
public class PacketInLoginStart extends AbstractPacket implements PacketLogin, PacketInbound {
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int getDefaultID() {
		return IN_LOGIN_START;
	}

}
