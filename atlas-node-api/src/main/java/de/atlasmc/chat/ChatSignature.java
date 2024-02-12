package de.atlasmc.chat;

public class ChatSignature {
	
	public final int id;
	
	public final byte[] signature;
	
	public ChatSignature(int id, byte[] signature) {
		this.id = id;
		this.signature = signature;
	}

}
