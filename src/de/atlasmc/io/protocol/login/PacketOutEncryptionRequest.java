package de.atlasmc.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketLogin.OUT_ENCRYPTION_REQUEST)
public interface PacketOutEncryptionRequest extends PacketLogin, PacketOutbound {
	
	public String getServerID();
	public byte[] getPublicKey();
	public byte[] getVerifyToken();
	
	@Override
	default int getDefaultID() {
		return OUT_ENCRYPTION_REQUEST;
	}

}
