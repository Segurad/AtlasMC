package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_ADVANCEMENT_TAB)
public interface PacketInAdvancementTab extends PacketPlay, PacketInbound {
	
	public int getAction();
	public String getTabID();
	
	@Override
	public default int getDefaultID() {
		return IN_ADVANCEMENT_TAB;
	}

}
