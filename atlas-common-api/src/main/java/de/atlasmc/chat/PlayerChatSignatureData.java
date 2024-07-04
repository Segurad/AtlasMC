package de.atlasmc.chat;

import java.security.PublicKey;
import java.util.UUID;

public class PlayerChatSignatureData {
	
	private final UUID sessionID;
	private final long keyExpiration;
	private final PublicKey publicKey;
	private final byte[] signature;

	public PlayerChatSignatureData(UUID sessionID, long keyExpiration, PublicKey key, byte[] signature) {
		this.sessionID = sessionID;
		this.keyExpiration = keyExpiration;
		this.publicKey = key;
		this.signature = signature.clone();
	}
	
	public UUID getSessionID() {
		return sessionID;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	public long getKeyExpiration() {
		return keyExpiration;
	}
	
	public byte[] getSignature() {
		return signature.clone();
	}
	
	public boolean matchSignature(byte[] signature) {
		return this.signature.equals(signature);
	}
	
}
