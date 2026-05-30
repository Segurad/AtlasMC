package de.atlasmc.node.io.protocol.handshake;

public record HandshakeData(long timestamp, int version, String address, int port, int intent) {

	public static final int
	INTENT_STATUS = 1,
	INTENT_LOGIN = 2,
	INTENT_TRANSFER = 3;
	
}
