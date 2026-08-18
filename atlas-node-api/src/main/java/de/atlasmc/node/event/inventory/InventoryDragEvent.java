package de.atlasmc.node.event.inventory;

import java.util.Map;

import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryView;
import de.atlasmc.node.inventory.ItemStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class InventoryDragEvent extends InventoryInteractEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final ItemStack newCursor;
	private final ItemStack oldCursor;
	private final DragType dragType;
	private final Int2ObjectMap<ItemStack> slots;
	
	public InventoryDragEvent(InventoryView view, Inventory inv, ItemStack newCursor, ItemStack oldCursor, boolean right, Int2ObjectMap<ItemStack> slots) {
		super(view, inv);
		this.newCursor = newCursor;
		this.oldCursor = oldCursor;
		this.slots = slots;
		this.dragType = right ? DragType.SINGLE : DragType.EVEN;
	}
	
	public ItemStack getOldCursor() {
		return oldCursor;
	}
	
	public ItemStack getNewCursor() {
		return newCursor;
	}
	
	/**
	 * 
	 * @return all changed raw slots mapped to items
	 */
	public Map<Integer, ItemStack> getNewItems() {
		return slots;
	}
	
	public DragType getDragType() {
		return dragType;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

	
	public static enum DragType {
		EVEN, 
		SINGLE
	}
	
}
