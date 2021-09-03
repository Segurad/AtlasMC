package de.atlascore.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;

public class CoreInventory implements Inventory {

	protected final ItemStack[] contents;
	protected final List<Player> viewers;
	private final int size;
	private InventoryHolder holder;
	private final InventoryType type;
	private final String title;
	
	public CoreInventory(int size, InventoryType type, String title, InventoryHolder holder) {
		this.size = size;
		this.contents = new ItemStack[size];
		this.viewers = new ArrayList<Player>(1);
		this.holder = holder;
		this.title = title;
		this.type = type;
	}
	
	@Override
	public Iterator<ItemStack> iterator() {
		return null; // TODO inventory iterator
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
		return contents[slot];
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
		return title;
	}

	@Override
	public SlotType getSlotType(int slot) {
		return SlotType.CONTAINER;
	}

	@Override
	public InventoryType getType() {
		return type;
	}

	@Override
	public InventoryHolder getHolder() {
		return holder;
	}

	@Override
	public void setHolder(InventoryHolder holder) {
		this.holder = holder;
	}

	@Override
	public ItemStack[] getContents() {
		return contents;
	}

	@Override
	public void setContents(ItemStack[] contents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Material material) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int count(Material material) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateSlots() {
		// TODO Auto-generated method stub
		
	}

}
