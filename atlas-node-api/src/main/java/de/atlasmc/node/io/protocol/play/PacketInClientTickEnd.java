package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CLIENT_TICK_END, definition = "client_tick_end")
public class PacketInClientTickEnd extends AbstractPacket implements PacketPlayIn {

	@Override
	public int getDefaultID() {
		return IN_CLIENT_TICK_END;
	}

}
