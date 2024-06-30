package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_DISCONNECT)
public class PacketOutDisconnect extends AbstractPacket implements PacketPlayOut {
	
	private String reason;
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public void setReason(Chat chat) {
		this.reason = chat.toText();
	}
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
