package de.atlasmc.io.protocol.login;

import de.atlasmc.io.Packet;

public interface PacketOutEncryptionRequest extends Packet {
	
	public String getServerID();
	public byte[] getPublicKey();
	public byte[] getVerifyToken();
	
	@Override
	default int getDefaultID() {
		return 0x01;
	}

}
