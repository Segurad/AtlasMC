package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_KEEP_ALIVE)
public class PacketInKeepAlive extends AbstractPacket implements PacketPlayIn {
	
	private long keepAliveID;
	
	public long getKeepAliveID() {
		return keepAliveID;
	}
	
	public void setKeepAliveID(long keepAliveID) {
		this.keepAliveID = keepAliveID;
	}

	@Override
	public int getDefaultID() {
		return IN_KEEP_ALIVE;
	}
	
}
