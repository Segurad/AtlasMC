package de.atlasmc.io.protocol.login;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketLogin.OUT_SET_COMPRESSION, definition = "login_compression")
public class PacketOutSetCompression extends AbstractPacket implements PacketLoginOut {
	
	public int threshold;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_COMPRESSION;
	}

}
