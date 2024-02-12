package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_DISGUISED_CHAT_MESSAGE)
public class PacketOutDisguisedChatMessage extends AbstractPacket implements PacketPlayOut {

	public String message;
	public int chatType;
	public String source;
	public String target;
	
	@Override
	public int getDefaultID() {
		return OUT_DISGUISED_CHAT_MESSAGE;
	}
	
}
