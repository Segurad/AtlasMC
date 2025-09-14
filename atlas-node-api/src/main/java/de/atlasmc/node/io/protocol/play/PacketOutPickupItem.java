package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_PICKUP_ITEM, definition = "take_item_entity")
public class PacketOutPickupItem extends AbstractPacket implements PacketPlayOut {
	
	public int collectedID;
	public int collectorID;
	public int pickupCount;
	
	@Override
	public int getDefaultID() {
		return OUT_PICKUP_ITEM;
	}

}
