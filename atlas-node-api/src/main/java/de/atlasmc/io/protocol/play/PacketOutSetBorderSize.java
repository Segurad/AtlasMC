package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_BORDER_SIZE, definition = "set_border_size")
public class PacketOutSetBorderSize extends AbstractPacket implements PacketPlayOut {

	public double diameter;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_BORDER_SIZE;
	}
	
}
