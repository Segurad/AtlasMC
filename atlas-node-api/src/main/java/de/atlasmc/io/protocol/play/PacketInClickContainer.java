package de.atlasmc.io.protocol.play;

import java.util.Map;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CLICK_CONTAINER)
public class PacketInClickContainer extends AbstractPacket implements PacketPlayIn {

	private int windowID;
	private int stateID;
	private int slot;
	private int button;
	private int mode;
	private Map<Integer, ItemStack> slotsChanged;
	private ItemStack carriedItem;

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
	
	public int getButton() {
		return button;
	}
	
	public void setButton(int button) {
		this.button = button;
	}

	public int getStateID() {
		return stateID;
	}
	
	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	public int getMode() {
		return mode;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public ItemStack getCarriedItem() {
		return carriedItem;
	}
	
	public void setCarriedItem(ItemStack carriedItem) {
		this.carriedItem = carriedItem;
	}

	public Map<Integer, ItemStack> getSlotsChanged() {
		return slotsChanged;
	}
	
	public void setSlotsChanged(Map<Integer, ItemStack> slotsChanged) {
		this.slotsChanged = slotsChanged;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CLICK_CONTAINER;
	}

}
