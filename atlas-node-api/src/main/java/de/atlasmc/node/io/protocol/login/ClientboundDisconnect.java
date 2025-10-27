package de.atlasmc.node.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketText;

@DefaultPacketID(packetID = PacketLogin.OUT_DISCONNECT, definition = "login_disconnect")
public class ClientboundDisconnect extends AbstractPacketText implements PacketLoginClientbound {
	
	@Override
	public boolean isTerminating() {
		return true;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
