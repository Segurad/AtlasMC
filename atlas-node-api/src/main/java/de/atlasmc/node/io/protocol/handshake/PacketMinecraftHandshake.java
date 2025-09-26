package de.atlasmc.node.io.protocol.handshake;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.handshake.PacketHandshake;

@DefaultPacketID(packetID = 0x00, definition = "intention")
public class PacketMinecraftHandshake extends AbstractPacket implements PacketHandshake {

	public int protocolVersion;
	public String address;
	public int port;
	public int nextState;
	
	@Override
	public int getDefaultID() {
		return 0x00;
	}

}
