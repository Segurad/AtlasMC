package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PONG)
public class PacketInPong extends AbstractPacket implements PacketPlayIn {

	public int id;
	
	@Override
	public int getDefaultID() {
		return PacketPlay.IN_PONG;
	}

}
