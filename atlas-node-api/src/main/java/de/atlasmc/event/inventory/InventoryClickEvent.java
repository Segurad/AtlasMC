package de.atlasmc.event.inventory;

import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.InventoryType.SlotType;

public class InventoryClickEvent extends InventoryInteractEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	private final InventoryAction action;
	private final ClickType click;
	private final int rawSlot;
	private final int key;
	
	public InventoryClickEvent(InventoryView view, int rawSlot, ClickType click, InventoryAction action) {
		this(view, rawSlot, click, action, -1);
	}
	
	public InventoryClickEvent(InventoryView view, int rawSlot, ClickType click, InventoryAction action, int key) {
		super(view);
		this.action = action;
		this.click = click;
		this.rawSlot = rawSlot;
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
		return view.getInventory(rawSlot);
	}

	public ClickType getClick() {
		return click;
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

	/**
	 * Returns the clicked slot in the clicked inventory or -999 if left or right out of the inventory
	 * @return clicked slot or -999
	 */
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
		return view.getSlotType(rawSlot);
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
