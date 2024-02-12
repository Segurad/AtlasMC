package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_BORDER_WARNING_DISTANCE)
public class PacketOutSetBorderWarningDistance extends AbstractPacket implements PacketPlayOut {

	public int distance;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_BORDER_WARNING_DISTANCE;
	}
	
}
