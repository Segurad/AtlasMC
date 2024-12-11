package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_STEP_TICK, definition = "ticking_step")
public class PacketOutStepTick extends AbstractPacket implements PacketPlayOut {

	public int steps;
	
	@Override
	public int getDefaultID() {
		return OUT_STEP_TICK;
	}
	
}
