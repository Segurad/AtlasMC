package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CLICK_WINDOW)
public class PacketInClickWindow extends AbstractPacket implements PacketPlayIn {

	private int windowID;
	private int button;
	private int slot;
	private int action;
	private int mode;
	private ItemStack clickedItem;

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

	public int getAction() {
		return action;
	}
	
	public void setAction(int action) {
		this.action = action;
	}

	public int getMode() {
		return mode;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public ItemStack getClickedItem() {
		return clickedItem;
	}
	
	public void setClickedItem(ItemStack clickedItem) {
		this.clickedItem = clickedItem;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CLICK_WINDOW;
	}
}
