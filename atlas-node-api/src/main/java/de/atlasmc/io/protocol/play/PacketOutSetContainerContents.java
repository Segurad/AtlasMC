package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_CONTAINER_CONTENT)
public class PacketOutSetContainerContents extends AbstractPacket implements PacketPlayOut {
	
	private int windowID;
	private int stateID;
	private ItemStack[] items;
	private ItemStack carriedItem;
	
	public int getWindowID() {
		return windowID;
	}
	
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}
	
	public ItemStack[] getItems() {
		return items;
	}
	
	public void setItems(ItemStack... items) {
		this.items = items;
	}
	
	public ItemStack getCarriedItem() {
		return carriedItem;
	}
	
	public void setCarriedItem(ItemStack carriedItem) {
		this.carriedItem = carriedItem;
	}
	
	public int getStateID() {
		return stateID;
	}
	
	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public int getDefaultID() {
		return OUT_SET_CONTAINER_CONTENT;
	}
	
}
