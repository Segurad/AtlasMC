package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_OPEN_BOOK)
public class PacketOutOpenBook extends AbstractPacket implements PacketPlayOut {
	
	private EquipmentSlot hand;
	
	public EquipmentSlot getHand() {
		return hand;
	}
	
	public void setHand(EquipmentSlot hand) {
		this.hand = hand;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_OPEN_BOOK;
	}

}
