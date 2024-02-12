package de.atlasmc.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SELECT_ADVANCEMENT_TAB)
public class PacketOutSelectAdvancementTab extends AbstractPacket implements PacketPlayOut {
	
	private NamespacedKey tabID;
	
	public NamespacedKey getTabID() {
		return tabID;
	}
	
	public void setTabID(NamespacedKey tabID) {
		this.tabID = tabID;
	}

	@Override
	public int getDefaultID() {
		return OUT_SELECT_ADVANCEMENT_TAB;
	}

}
