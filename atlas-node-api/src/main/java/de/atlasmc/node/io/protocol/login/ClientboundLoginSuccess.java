package de.atlasmc.node.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.network.player.PlayerProfile;

@DefaultPacketID(packetID = PacketLogin.OUT_LOGIN_SUCCESS, definition = "login_finished")
public class ClientboundLoginSuccess extends AbstractPacket implements PacketLoginClientbound {
	
	public PlayerProfile profile;
	
	@Override
	public int getDefaultID() {
		return OUT_LOGIN_SUCCESS;
	}

}
