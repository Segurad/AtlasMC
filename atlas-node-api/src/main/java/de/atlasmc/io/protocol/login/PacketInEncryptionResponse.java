package de.atlasmc.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketLogin.IN_ENCRYPTION_RESPONSE, definition = "key")
public class PacketInEncryptionResponse extends AbstractPacket implements PacketLoginIn {
	
	public byte[] secret;
	public byte[] verifyToken;
	
	@Override
	public int getDefaultID() {
		return IN_ENCRYPTION_RESPONSE;
	}

}
