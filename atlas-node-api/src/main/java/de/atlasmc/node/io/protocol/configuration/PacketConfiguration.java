package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.node.io.protocol.PacketProtocol;

public interface PacketConfiguration extends PacketProtocol {
	
	public static final int
	PACKET_COUNT_IN = 8,
	PACKET_COUNT_OUT = 17;
	
	public static final int 
	IN_CLIENT_INFORMATION = 0x00,
	IN_COOKIE_RESPONSE = 0x01,
	IN_PLUGIN_MESSAGE = 0x02,
	IN_FINISH_CONFIGURATION = 0x03,
	IN_KEEP_ALIVE = 0x04,
	IN_PONG = 0x05,
	IN_RESOURCE_PACK = 0x06,
	IN_KNOWN_PACKS = 0x07;
	
	public static final int 
	OUT_COOKIE_REQUEST = 0x00,
	OUT_PLUGIN_MESSAGE = 0x01,
	OUT_DISCONNECT = 0x02,
	OUT_FINISH_CONFIGURATION = 0x03,
	OUT_KEEP_ALIVE = 0x04,
	OUT_PING = 0x05,
	OUT_RESET_CHAT = 0x06,
	OUT_REGISTRY_DATA = 0x07,
	OUT_REMOVE_RESOURCE_PACK = 0x08,
	OUT_ADD_RESOURCE_PACK = 0x09,
	OUT_STORE_COOKIE = 0x0A,
	OUT_TRANSFER = 0x0B,
	OUT_FEATURE_FLAGS = 0x0C,
	OUT_UPDATE_TAGS = 0x0D,
	OUT_KNOWN_PACKS = 0x0E,
	OUT_CUSTOM_REPORT_DETAILS = 0x0F,
	OUT_SERVER_LINKS = 0x10;

}
