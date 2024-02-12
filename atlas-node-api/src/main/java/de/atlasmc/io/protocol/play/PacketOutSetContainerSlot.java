package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_CONTAINER_SLOT)
public class PacketOutSetContainerSlot extends AbstractPacket implements PacketPlayOut {

	private int windowID, slot, stateID;
	private ItemStack item;
	
	public int getWindowID() {
		return windowID;
	}
	
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public void setItem(ItemStack item) {
		this.item = item;
	}
	
	public int getStateID() {
		return stateID;
	}
	
	public void setStateID(int stateID) {
		this.stateID = stateID;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_CONTAINER_SLOT;
	}
	
}
