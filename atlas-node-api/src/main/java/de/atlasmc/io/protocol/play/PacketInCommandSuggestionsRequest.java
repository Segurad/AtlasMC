package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_COMMAND_SUGGESTIONS_REQUEST)
public class PacketInCommandSuggestionsRequest extends AbstractPacket implements PacketPlayIn {

	private int transactionID;
	private String text;
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public int getDefaultID() {
		return IN_COMMAND_SUGGESTIONS_REQUEST;
	}
	
}
