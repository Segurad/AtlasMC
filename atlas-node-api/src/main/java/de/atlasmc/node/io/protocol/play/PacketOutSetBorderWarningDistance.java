package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_BORDER_WARNING_DISTANCE, definition = "set_border_warning_distance")
public class PacketOutSetBorderWarningDistance extends AbstractPacket implements PacketPlayOut {

	public int distance;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_BORDER_WARNING_DISTANCE;
	}
	
}
