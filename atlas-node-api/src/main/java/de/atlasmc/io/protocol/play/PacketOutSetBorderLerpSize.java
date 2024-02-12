package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_BORDER_LERP_SIZE)
public class PacketOutSetBorderLerpSize extends AbstractPacket implements PacketPlayOut {

	public double oldDiameter;
	public double newDiameter;
	public long speed;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_BORDER_LERP_SIZE;
	}
	
}
