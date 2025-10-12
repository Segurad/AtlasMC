package de.atlasmc.node.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketDisconnect;

@DefaultPacketID(packetID = PacketLogin.OUT_DISCONNECT, definition = "login_disconnect")
public class ClientboundDisconnect extends AbstractPacketDisconnect implements PacketLoginClientbound {
	
	@Override
	public boolean isTerminating() {
		return true;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
