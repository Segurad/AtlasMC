package de.atlasmc.node.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketLogin.OUT_ENCRYPTION_REQUEST, definition = "hello")
public class PacketOutEncryptionRequest extends AbstractPacket implements PacketLoginOut {
	
	public String serverID;
	public byte[] publicKey;
	public byte[] verifyToken;
	public boolean authenticate;
	
	@Override
	public int getDefaultID() {
		return OUT_ENCRYPTION_REQUEST;
	}

}
