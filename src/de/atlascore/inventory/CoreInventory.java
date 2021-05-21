package de.atlascore.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PlayerConnection;

public class CoreInventory implements Inventory {

	protected final ItemStack[] contents;
	protected final List<Player> viewers;
	protected final int size;
	
	public CoreInventory(int size) {
		this.size = size;
		this.contents = new ItemStack[size];
		this.viewers = new ArrayList<Player>(1);
	}
	
	@Override
	public Iterator<ItemStack> iterator() {
		return null;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void setItem(int slot, ItemStack item) {
		contents[slot] = item;
		if (!viewers.isEmpty()) {
			updateSlot(slot);
		}
	}

	@Override
	public ItemStack getItem(int slot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> getViewers() {
		return viewers;
	}

	@Override
	public void updateSlot(int slot) {
		for (Player player : viewers) {
			InventoryView view = player.getOpenInventory();
			if (view.getBottomInventory() == this) {
				
			} else if (view.getTopInventory() == this) {
				
			} else viewers.remove(player);
		}
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SlotType getSlotType(int slot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryType getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
