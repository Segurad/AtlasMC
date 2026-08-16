package de.atlasmc.network.io.protocol;

public interface AtlasPacket {

	public static final int
	CLIENT_NETWORK_STATS = 0x00,
	CLIENT_RESPONSE_TYPE = 0x01;
	
	public static final int
	SERVER_REQUEST_TYPE_BY_UUID = 0x00,
	SERVER_REQUEST_TYPE_BY_NAME = 0x00;
	
}
