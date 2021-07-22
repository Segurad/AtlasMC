package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_COMBAT_EVENT)
public interface PacketOutCombatEvent extends PacketPlay, PacketOutbound {
	
	public int getEvent();
	public int getDuration();
	public int getPlayerID();
	public int getEntityID();
	public String getDeathMessage();
	
	@Override
	public default int getDefaultID() {
		return OUT_COMBAT_EVENT;
	}

}
