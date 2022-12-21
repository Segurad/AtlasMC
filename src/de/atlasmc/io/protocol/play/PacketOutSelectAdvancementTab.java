package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SELECT_ADVANCEMENT_TAB)
public class PacketOutSelectAdvancementTab extends AbstractPacket implements PacketPlayOut {
	
	private String tabID;
	
	public String getTabID() {
		return tabID;
	}
	
	public void setTabID(String tabID) {
		this.tabID = tabID;
	}

	@Override
	public int getDefaultID() {
		return OUT_SELECT_ADVANCEMENT_TAB;
	}

}
