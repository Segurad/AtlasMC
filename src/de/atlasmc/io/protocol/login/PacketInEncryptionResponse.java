package de.atlasmc.io.protocol.login;

import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(0x01)
public interface PacketInEncryptionResponse extends PacketLogin, PacketInbound {
	
	public byte[] getSecret();
	public byte[] getVerifyToken();
	
	@Override
	public default int getDefaultID() {
		return 0x01;
	}

}
