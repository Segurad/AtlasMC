package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_TICKING_STATE, definition = "ticking_state")
public class PacketOutSetTickingState extends AbstractPacket implements PacketPlayOut {

	public float tickRate;
	public boolean frozen;
	
	@Override
	public int getDefaultID() {
		return OUT_TICKING_STATE;
	}

}
