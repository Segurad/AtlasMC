package de.atlasmc.io.protocol.status;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketStatus.IN_PING)
public class PacketInPing extends AbstractPacket implements PacketStatus, PacketInbound {
	
	private long ping;
	
	public long getPing() {
		return ping;
	}
	
	public void setPing(long ping) {
		this.ping = ping;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PING;
	}

}
