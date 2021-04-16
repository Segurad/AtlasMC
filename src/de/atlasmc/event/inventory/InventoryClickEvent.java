package de.atlasmc.event.inventory;

import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;

public class InventoryClickEvent extends InventoryInteractEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	private final InventoryAction action;
	private final ClickType click;
	private final int rawSlot, key;
	private final SlotType slotType;
	
	public InventoryClickEvent(InventoryView view, SlotType type, int slot, ClickType click, InventoryAction action) {
		this(view, type, slot, click, action, -1);
	}
	
	public InventoryClickEvent(InventoryView view, SlotType type, int slot, ClickType click, InventoryAction action, int key) {
		super(view);
		this.action = action;
		this.click = click;
		this.rawSlot = slot;
		this.slotType = type;
		this.key = key;
	}

	public ItemStack getCurrentItem() {
		return view.getItem(rawSlot);
	}
	
	public void setCurrentItem(ItemStack item) {
		view.setItem(rawSlot, item);
	}

	public ItemStack getCursor() {
		return view.getCursor();
	}
	
	public void setCursor(ItemStack item) {
		view.setCursor(item);
	}

	public Inventory getClickedInventory() {
		return null;
	}

	public ClickType getClick() {
		return null;
	}
	
	public boolean isLeftClick() {
		return click.isLeftClick();
	}
	
	public boolean isRightClick() {
		return click.isRightClick();
	}
	
	public boolean isShiftClick() {
		return click.isShiftClick();
	}
	
	public InventoryAction getAction() {
		return action;
	}

	public int getSlot() {
		return view.convertSlot(rawSlot);
	}
	
	public int getRawSlot() {
		return rawSlot;
	}

	public int getHotbarButton() {
		return key;
	}
	
	public SlotType getSlotType() {
		return slotType;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
