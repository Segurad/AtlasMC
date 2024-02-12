package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_BORDER_CENTER)
public class PacketOutSetBorderCenter extends AbstractPacket implements PacketPlayOut {

	public double x;
	public double z;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_BORDER_CENTER;
	}
	
}
