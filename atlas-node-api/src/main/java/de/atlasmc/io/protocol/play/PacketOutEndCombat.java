package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_END_COMBAT, definition = "player_combat_end")
public class PacketOutEndCombat extends AbstractPacket implements PacketPlayOut {

	public int duration;
	
	@Override
	public int getDefaultID() {
		return OUT_END_COMBAT;
	}
	
}
