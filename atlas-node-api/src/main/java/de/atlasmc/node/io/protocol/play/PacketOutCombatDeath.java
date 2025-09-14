package de.atlasmc.node.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_COMBAT_DEATH, definition = "player_combat_kill")
public class PacketOutCombatDeath extends AbstractPacket implements PacketPlayOut {

	public int playerID;
	public Chat deathMessage;
	
	@Override
	public int getDefaultID() {
		return OUT_COMBAT_DEATH;
	}
	
}
