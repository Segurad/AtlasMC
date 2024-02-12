package de.atlasmc.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketLogin.OUT_SET_COMPRESSION)
public class PacketOutSetCompression extends AbstractPacket implements PacketLoginOut {
	
	public int threshold;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_COMPRESSION;
	}

}
