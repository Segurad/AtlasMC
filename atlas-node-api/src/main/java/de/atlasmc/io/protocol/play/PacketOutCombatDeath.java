package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_COMBAT_DEATH)
public class PacketOutCombatDeath extends AbstractPacket implements PacketPlayOut {

	public int playerID;
	public String deathMessage;
	
	@Override
	public int getDefaultID() {
		return OUT_COMBAT_DEATH;
	}
	
}
