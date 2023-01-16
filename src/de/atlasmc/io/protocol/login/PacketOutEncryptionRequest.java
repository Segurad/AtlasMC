package de.atlasmc.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketLogin.OUT_ENCRYPTION_REQUEST)
public class PacketOutEncryptionRequest extends AbstractPacket implements PacketLogin, PacketOutbound {
	
	private String serverID;
	private byte[] publicKey;
	private byte[] verifyToken;
	
	public String getServerID() {
		return serverID;
	}
	
	public void setServerID(String serverID) {
		this.serverID = serverID;
	}
	
	public byte[] getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}
	
	public byte[] getVerifyToken() {
		return verifyToken;
	}
	
	public void setVerifyToken(byte[] verifyToken) {
		this.verifyToken = verifyToken;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_ENCRYPTION_REQUEST;
	}

}
