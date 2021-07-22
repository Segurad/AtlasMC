package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_COLLECT_ITEM)
public interface PacketOutCollectItem extends PacketPlay, PacketOutbound {
	
	public int getCollectedID();
	public int getCollectorID();
	public int getPickupCount();
	
	@Override
	public default int getDefaultID() {
		return OUT_COLLECT_ITEM;
	}

}
