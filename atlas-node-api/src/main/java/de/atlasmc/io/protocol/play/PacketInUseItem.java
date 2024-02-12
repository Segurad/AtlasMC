package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_USE_ITEM)
public class PacketInUseItem extends AbstractPacket implements PacketPlayIn {
	
	private EquipmentSlot hand;
	private int sequence;
	
	public EquipmentSlot getHand() {
		return hand;
	}
	
	public void setHand(EquipmentSlot hand) {
		this.hand = hand;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public int getDefaultID() {
		return IN_USE_ITEM;
	}
	
}
