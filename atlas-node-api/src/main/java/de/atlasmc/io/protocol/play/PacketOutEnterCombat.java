package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_ENTER_COMBAT)
public class PacketOutEnterCombat extends AbstractPacket implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_ENTER_COMBAT;
	}
	
}
