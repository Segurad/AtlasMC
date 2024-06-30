package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SYSTEM_CHAT_MESSAGE)
public class PacketOutSystemChatMessage extends AbstractPacket implements PacketPlayOut {

	private String message;
	private boolean actionbar;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMessage(Chat message) {
		this.message = message.toText();
	}
	
	public boolean isActionbar() {
		return actionbar;
	}
	
	public void setActionbar(boolean actionbar) {
		this.actionbar = actionbar;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SYSTEM_CHAT_MESSAGE;
	}
	
}
