package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_END_COMBAT)
public class PacketOutEndCombat extends AbstractPacket implements PacketPlayOut {

	public int duration;
	
	@Override
	public int getDefaultID() {
		return OUT_END_COMBAT;
	}
	
}
