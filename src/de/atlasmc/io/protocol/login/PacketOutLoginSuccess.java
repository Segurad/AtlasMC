package de.atlasmc.io.protocol.login;

import java.util.UUID;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketLogin.OUT_LOGIN_SUCCESS)
public class PacketOutLoginSuccess extends AbstractPacket implements PacketLogin, PacketOutbound {
	
	private UUID uuid;
	private String username;
	
	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_LOGIN_SUCCESS;
	}

}
