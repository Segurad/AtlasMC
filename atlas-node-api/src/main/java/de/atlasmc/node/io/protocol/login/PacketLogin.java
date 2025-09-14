package de.atlasmc.node.io.protocol.login;

import de.atlasmc.node.io.protocol.PacketProtocol;

/**
 * This interface marks Packets for the mc-login protocol
 */
public interface PacketLogin extends PacketProtocol {
	
	public static final int
	PACKET_COUNT_IN = 5,
	PACKET_COUNT_OUT = 6;
	
	public static final int 
	IN_LOGIN_START = 0x00,
	IN_ENCRYPTION_RESPONSE = 0x01,
	IN_LOGIN_PLUGIN_RESPONSE = 0x02,
	IN_LOGIN_ACKNOWLEDGED = 0x03,
	IN_COOKIE_RESPONSE = 0x04;
	
	public static final int 
	OUT_DISCONNECT= 0x00,
	OUT_ENCRYPTION_REQUEST = 0x01,
	OUT_LOGIN_SUCCESS = 0x02,
	OUT_SET_COMPRESSION = 0x03,
	OUT_LOGIN_PLUGIN_REQUEST = 0x04,
	OUT_COOKIE_REQUEST = 0x05;

}
