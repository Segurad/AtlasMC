package de.atlasmc.chat;

import java.security.PublicKey;
import java.util.UUID;

public interface PlayerChatSignatureData {
	
	UUID getSessionID();
	
	PublicKey getPublicKey();
	
	long getKeyExpiration();
	
	byte[] getSignature();
	
}
