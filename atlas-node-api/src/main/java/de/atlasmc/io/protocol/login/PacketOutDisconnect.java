package de.atlasmc.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketLogin.OUT_DISCONNECT)
public class PacketOutDisconnect extends AbstractPacket implements PacketLoginOut {
	
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
