package de.atlasmc.io.protocol.login;

import java.util.List;
import java.util.UUID;

import de.atlasmc.atlasnetwork.player.ProfileProperty;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketLogin.OUT_LOGIN_SUCCESS, definition = "login_finished")
public class PacketOutLoginSuccess extends AbstractPacket implements PacketLoginOut {
	
	public UUID uuid;
	public String username;
	public List<ProfileProperty> properties;
	
	@Override
	public int getDefaultID() {
		return OUT_LOGIN_SUCCESS;
	}

}
