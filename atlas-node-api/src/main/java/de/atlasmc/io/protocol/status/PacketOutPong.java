package de.atlasmc.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketStatus.OUT_PONG)
public class PacketOutPong extends AbstractPacket implements PacketStatus, PacketOutbound {
	
	public long pong;
	
	@Override
	public int getDefaultID() {
		return OUT_PONG;
	}

}
