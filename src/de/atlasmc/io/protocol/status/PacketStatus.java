package de.atlasmc.io.protocol.status;

import de.atlasmc.io.protocol.PacketProtocol;

/**
 * This interface marks Packets for the mc-status protocol
 */
public interface PacketStatus extends PacketProtocol {
	
	public static final int
	IN_REQUEST = 0x00,
	IN_PING = 0x01;
	
	public static final int
	OUT_RESPONSE = 0x00,
	OUT_PONG = 0x01;

}
