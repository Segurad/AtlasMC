package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_EDIT_BOOK)
public class PacketInEditBook extends AbstractPacket implements PacketPlayIn {
	
	private ItemStack book;
	private boolean isSigning;
	private EquipmentSlot hand;
	
	public ItemStack getBook() {
		return book;
	}
	
	public void setBook(ItemStack book) {
		this.book = book;
	}
	
	public EquipmentSlot getHand() {
		return hand;
	}
	
	public void setHand(EquipmentSlot hand) {
		if (hand == null)
			throw new IllegalArgumentException("Hand can not be null!");
		if (hand != EquipmentSlot.HAND && hand != EquipmentSlot.OFF_HAND)
			throw new IllegalArgumentException("Illegal EquipmentSlot: " + hand.name());
		this.hand = hand;
	}
	
	public boolean isSigning() {
		return isSigning;
	}
	
	public void setSigning(boolean signing) {
		this.isSigning = signing;
	}
	
	@Override
	public int getDefaultID() {
		return IN_EDIT_BOOK;
	}

}
