package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_TIME, definition = "set_time")
public class PacketOutUpdateTime extends AbstractPacket implements PacketPlayOut {
	
	public long worldAge;
	public long timeOfDay;
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_TIME;
	}

}
