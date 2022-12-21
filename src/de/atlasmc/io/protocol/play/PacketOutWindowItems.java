package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_WINDOW_ITEMS)
public class PacketOutWindowItems extends AbstractPacket implements PacketPlayOut {
	
	private int windowID;
	private ItemStack[] items;
	
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

	@Override
	public int getDefaultID() {
		return OUT_WINDOW_ITEMS;
	}
	
}
