package de.atlasmc.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketLogin.OUT_SET_COMPRESSION)
public interface PacketOutSetCompression extends PacketLogin, PacketOutbound {
	
	public int getThreshold();
	
	@Override
	public default int getDefaultID() {
		return OUT_SET_COMPRESSION;
	}

}
