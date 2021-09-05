package de.atlasmc.io.protocol.login;

import de.atlasmc.io.protocol.PacketProtocol;

/**
 * This interface marks Packets for the mc-login protocol
 */
public interface PacketLogin extends PacketProtocol {
	
	public static final int 
	IN_LOGIN_START = 0x00,
	IN_ENCRYPTION_RESPONSE = 0x01,
	IN_LOGIN_PLUGIN_RESPONSE = 0x02;
	
	public static final int 
	OUT_DISCONNECT= 0x00,
	OUT_ENCRYPTION_REQUEST = 0x01,
	OUT_LOGIN_SUCCESS = 0x02,
	OUT_SET_COMPRESSION = 0x03,
	OUT_LOGIN_PLUGIN_REQUEST = 0x04;

}
