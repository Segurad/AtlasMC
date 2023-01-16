package de.atlasmc.io.protocol.login;

import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketLogin.IN_ENCRYPTION_RESPONSE)
public interface PacketInEncryptionResponse extends PacketLogin, PacketInbound {
	
	public byte[] getSecret();
	
	public byte[] getVerifyToken();
	
	public void setSecret(byte[] secret);
	
	public void setVerifyToken(byte[] secret);
	
	@Override
	public default int getDefaultID() {
		return IN_ENCRYPTION_RESPONSE;
	}

}
