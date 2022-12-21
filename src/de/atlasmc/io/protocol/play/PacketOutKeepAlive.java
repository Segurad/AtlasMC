package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_KEEP_ALIVE)
public class PacketOutKeepAlive extends AbstractPacket implements PacketPlayOut {

	private long keepAlive;
	
	public long getKeepAlive() {
		return keepAlive;
	}
	
	public void setKeepAlive(long keepAlive) {
		this.keepAlive = keepAlive;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_KEEP_ALIVE;
	}
	
}
