package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_ENTER_COMBAT, definition = "player_combat_enter")
public class PacketOutEnterCombat extends AbstractPacket implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_ENTER_COMBAT;
	}
	
}
