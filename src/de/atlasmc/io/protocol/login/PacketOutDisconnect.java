package de.atlasmc.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketLogin.OUT_DISCONNECT)
public class PacketOutDisconnect extends AbstractPacket implements PacketLogin, PacketOutbound {
	
	private String reason;
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
