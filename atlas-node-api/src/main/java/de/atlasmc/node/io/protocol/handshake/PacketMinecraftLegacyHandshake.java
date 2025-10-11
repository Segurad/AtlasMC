package de.atlasmc.node.io.protocol.handshake;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.handshake.PacketHandshake;

@DefaultPacketID(packetID = 0xFE, definition = "legacy_handshake")
public class PacketMinecraftLegacyHandshake extends AbstractPacket implements PacketHandshake {

	public int status;

	@Override
	public int getDefaultID() {
		return 0xFE;
	}
	
	@Override
	public boolean isTerminating() {
		return true;
	}
	
}
