package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_HEALTH, definition = "set_health")
public class PacketOutSetHealth extends AbstractPacket implements PacketPlayOut {
	
	public float health;
	public float saturation;
	public int food;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_HEALTH;
	}

}
