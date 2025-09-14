package de.atlasmc.node.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SELECT_ADVANCEMENT_TAB, definition = "select_advancements_tab")
public class PacketOutSelectAdvancementTab extends AbstractPacket implements PacketPlayOut {
	
	public NamespacedKey tabID;

	@Override
	public int getDefaultID() {
		return OUT_SELECT_ADVANCEMENT_TAB;
	}

}
