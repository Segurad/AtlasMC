package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_DEFAULT_SPAWN_POSITION)
public class PacketOutSetDefaultSpawnPosition extends AbstractPacket implements PacketPlayOut {
	
	private long position;
	private float angel;
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public float getAngel() {
		return angel;
	}
	
	public void setAngel(float angel) {
		this.angel = angel;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_DEFAULT_SPAWN_POSITION;
	}

}
