package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_BORDER_CENTER, definition = "set_border_center")
public class PacketOutSetBorderCenter extends AbstractPacket implements PacketPlayOut {

	public double x;
	public double z;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_BORDER_CENTER;
	}
	
}
