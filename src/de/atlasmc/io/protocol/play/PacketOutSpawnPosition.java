package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SPAWN_POSITION)
public class PacketOutSpawnPosition extends AbstractPacket implements PacketPlayOut {
	
	private long position;
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SPAWN_POSITION;
	}

}
