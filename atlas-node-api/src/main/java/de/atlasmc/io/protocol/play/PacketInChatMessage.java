package de.atlasmc.io.protocol.play;

import java.util.BitSet;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CHAT_MESSAGE)
public class PacketInChatMessage extends AbstractPacket implements PacketPlayIn {

	private String message;
	private long messageTimestamp;
	private long salt;
	private byte[] signature;
	private int messageCount;
	private BitSet acknowledged;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public long getMessageTimestamp() {
		return messageTimestamp;
	}
	
	public void setMessageTimestamp(long messageTimestamp) {
		this.messageTimestamp = messageTimestamp;
	}
	
	public long getSalt() {
		return salt;
	}
	
	public void setSalt(long salt) {
		this.salt = salt;
	}
	
	public byte[] getSignature() {
		return signature;
	}
	
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	
	public int getMessageCount() {
		return messageCount;
	}
	
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	
	public BitSet getAcknowledged() {
		return acknowledged;
	}
	
	public void setAcknowledged(BitSet acknowledged) {
		this.acknowledged = acknowledged;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CHAT_MESSAGE;
	}
	
}
