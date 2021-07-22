package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.entity.AbstractVillager.Trade;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_TRADE_LIST)
public interface PacketOutTradeList extends PacketPlay, PacketOutbound {
	
	public int getWindowID();
	public List<Trade> getTrades();
	public int getVillagerLevel();
	public int getExperience();
	public boolean isRegularVillager();
	public boolean getCanRestock();
	
	@Override
	default int getDefaultID() {
		return OUT_TRADE_LIST;
	}

}
