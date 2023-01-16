package de.atlasmc.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketStatus.OUT_RESPONSE)
public class PacketOutResponse extends AbstractPacket implements PacketStatus, PacketOutbound {
	
	private String response;
	
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_RESPONSE;
	}

}
