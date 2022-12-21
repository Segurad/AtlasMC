package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_COLLECT_ITEM)
public class PacketOutCollectItem extends AbstractPacket implements PacketPlayOut {
	
	private int collectedID, collectorID, pickupCount;
	
	public int getCollectedID() {
		return collectedID;
	}
	
	public void setCollectedID(int collectedID) {
		this.collectedID = collectedID;
	}
	
	public int getCollectorID() {
		return collectorID;
	}
	
	public void setCollectorID(int collectorID) {
		this.collectorID = collectorID;
	}
	
	public int getPickupCount() {
		return pickupCount;
	}
	
	public void setPickupCount(int pickupCount) {
		this.pickupCount = pickupCount;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_COLLECT_ITEM;
	}

}
