package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SELECT_ADVANCEMENT_TAB)
public interface PacketOutSelectAdvancementTab extends PacketPlay, PacketOutbound {
	
	public boolean hasTabID();
	public String getTabID();

	@Override
	public default int getDefaultID() {
		return OUT_SELECT_ADVANCEMENT_TAB;
	}

}
