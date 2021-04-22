package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.entity.AbstractVillager.Trade;
import de.atlasmc.io.Packet;

public interface PacketOutTradeList extends Packet {
	
	public int getWindowID();
	public List<Trade> getTrades();
	public int getVillagerLevel();
	public int getExperience();
	public boolean isRegularVillager();
	public boolean getCanRestock();
	
	@Override
	default int getDefaultID() {
		return 0x26;
	}

}
