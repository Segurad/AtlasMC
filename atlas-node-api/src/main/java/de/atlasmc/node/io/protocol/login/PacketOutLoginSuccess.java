package de.atlasmc.node.io.protocol.login;

import java.util.List;
import java.util.UUID;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.network.player.ProfileProperty;

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
