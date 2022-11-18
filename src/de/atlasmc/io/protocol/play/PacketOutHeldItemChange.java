package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_HELD_ITEM_CHANGE)
public interface PacketOutHeldItemChange extends PacketPlay, PacketOutbound {
	
	public int getSlot();
	
	public void setSlot(int slot);
	
	@Override
	public default int getDefaultID() {
		return OUT_HELD_ITEM_CHANGE;
	}

}
