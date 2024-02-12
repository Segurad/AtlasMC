package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.protocol.PacketProtocol;

public interface PacketConfiguration extends PacketProtocol {
	
	public static final int
	PACKET_COUNT_IN = 6,
	PACKET_COUNT_OUT = 9;
	
	public static final int 
	IN_CLIENT_INFORMATION = 0x00,
	IN_PLUGIN_MESSAGE = 0x01,
	IN_FINISH_CONFIGURATION = 0x02,
	IN_KEEP_ALIVE = 0x03,
	IN_PONG = 0x04,
	IN_RESOURCE_PACK = 0x05;
	
	public static final int 
	OUT_PLUGIN_MESSAGE = 0x00,
	OUT_DISCONNECT = 0x01,
	OUT_FINISH_CONFIGURATION = 0x02,
	OUT_KEEP_ALIVE = 0x03,
	OUT_PING = 0x04,
	OUT_REGISTRY_DATA = 0x05,
	OUT_RESOURCE_PACK = 0x06,
	OUT_FEATURE_FLAGS = 0x07,
	OUT_UPDATE_TAGS = 0x08;

}
