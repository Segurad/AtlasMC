package de.atlasmc.event.inventory;

import de.atlasmc.entity.Player;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;

public class InventoryClickEvent extends InventoryInteractEvent {

	public InventoryClickEvent(Inventory inv) {
		super(inv);
		// TODO Auto-generated constructor stub
	}

	public ItemStack getCurrentItem() {
		// TODO Auto-generated method stub
		return null;
	}

	public ItemStack getCursor() {
		// TODO Auto-generated method stub
		return null;
	}

	public Inventory getClickedInventory() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static enum ClickType {
		LEFT, RIGHT, SHIFT_RIGHT, SHIFT_LEFT, NUMBER_KEY, CONTROL_DROP, DROP, DOUBLE_CLICK
		
	}

	public ClickType getClick() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getSlot() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getHotbarButton() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Player getWhoClicked() {
		// TODO Auto-generated method stub
		return null;
	}

}
