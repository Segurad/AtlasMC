package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_BORDER_WARNING_DELAY, definition = "set_border_warning_delay")
public class PacketOutSetBorderWarningDelay extends AbstractPacket implements PacketPlayOut {

	public int delay;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_BORDER_WARNING_DELAY;
	}
	
}
