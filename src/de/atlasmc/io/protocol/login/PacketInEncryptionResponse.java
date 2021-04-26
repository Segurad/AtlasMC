package de.atlasmc.io.protocol.login;

import de.atlasmc.io.Packet;

public interface PacketInEncryptionResponse extends Packet {
	
	public byte[] getSecret();
	public byte[] getVerifyToken();
	
	@Override
	public default int getDefaultID() {
		return 0x01;
	}

}
