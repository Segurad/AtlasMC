package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutCombatEvent extends Packet {
	
	public int getEvent();
	public int getDuration();
	public int getPlayerID();
	public int getEntityID();
	public String getDeathMessage();
	
	@Override
	public default int getDefaultID() {
		return 0x31;
	}

}
