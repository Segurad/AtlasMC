package de.atlascore.chat;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.chat.PlayerChatSignatureData;

public class CorePlayerChatSignatureData implements PlayerChatSignatureData {
	
	private final UUID sessionID;
	private final long keyExpiration;
	private final PublicKey publicKey;
	private final byte[] signature;

	public CorePlayerChatSignatureData(UUID sessionID, long keyExpiration, PublicKey key, byte[] signature) {
		this.sessionID = sessionID;
		this.keyExpiration = keyExpiration;
		this.publicKey = key;
		this.signature = signature.clone();
	}
	
	@Override
	public UUID getSessionID() {
		return sessionID;
	}
	
	@Override
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	@Override
	public long getKeyExpiration() {
		return keyExpiration;
	}
	
	@Override
	public byte[] getSignature() {
		return signature.clone();
	}
	
}
