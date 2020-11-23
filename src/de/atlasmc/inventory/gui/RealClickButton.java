package de.atlasmc.inventory.gui;

import de.atlasmc.Material;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.event.inventory.InventoryClickEvent.ClickType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;

/**
 * this button is meant to listen what will happen in this event
 * the button does not modify the cursor or inventory
 * @author Segurad
 *
 */
public abstract class RealClickButton extends ActionButton {

	public static final int NOTIFY_CURSOR = 0,
			NOTIFY_SHIFT = 1,
			NOTIFY_DROP = 2;
	@Override
	public ItemStack press(InventoryClickEvent e) {
		ItemStack sitem = e.getCurrentItem(), citem = e.getCursor();
		if (sitem != null && sitem.getType() == Material.AIR) sitem = null;
		if (citem != null && citem.getType() == Material.AIR) citem = null;
		if (citem == null && sitem == null) return null;
		Inventory inv = e.getClickedInventory();
		ClickType click = e.getClick();
		boolean cancelled = e.isCancelled();
		int slot = e.getSlot();
		if (click == ClickType.LEFT) {
			if (sitem != null && citem != null && sitem.isSimilar(citem)) {
				ItemStack newsitem = sitem.clone();
				ItemStack newcitem = null;
				if (newsitem.getAmount() + citem.getAmount() > newsitem.getMaxStackSize()) {
					newcitem = citem.clone();
					newcitem.setAmount(newcitem.getAmount() - (newsitem.getMaxStackSize()-newsitem.getAmount()));
					newsitem.setAmount(sitem.getMaxStackSize());
				} else {
					newsitem.setAmount(newsitem.getAmount()+citem.getAmount());
				}
				cancelled = notifyClickedSlot(newsitem, sitem, inv, slot, cancelled, NOTIFY_CURSOR);
				cancelled = notifyCursor(newcitem, citem, newsitem, sitem, inv, slot, cancelled);
			} else {
				cancelled = notifyClickedSlot(citem, sitem, inv, slot, cancelled, NOTIFY_CURSOR);
				cancelled = notifyCursor(sitem, citem, citem, sitem, inv, slot, cancelled);
			}
		} else if (click == ClickType.RIGHT) {
			if (citem == null && sitem != null) {
				int newamount = sitem.getAmount()/2;
				ItemStack newsitem = null; 
				if(newamount > 0) {
					newsitem = sitem.clone();
					newsitem.setAmount(newamount);
				}
				ItemStack newcitem = sitem.clone();
				newcitem.setAmount(sitem.getAmount()-newamount);
				cancelled = notifyClickedSlot(newsitem, sitem, inv, slot, cancelled, NOTIFY_CURSOR);
				cancelled = notifyCursor(newcitem, null, newsitem, sitem, inv, slot, cancelled);
			} else if (sitem == null) {
				ItemStack newsitem = citem.clone();
				newsitem.setAmount(1);
				ItemStack newcitem = null;
				if (citem.getAmount() > 1) {
					newcitem = citem.clone();
					newcitem.setAmount(citem.getAmount()-1);
				}
				cancelled = notifyClickedSlot(newsitem, sitem, inv, slot, cancelled, NOTIFY_CURSOR);
				cancelled = notifyCursor(newcitem, null, newsitem, sitem, inv, slot, cancelled);
			} else if (sitem.isSimilar(citem) && sitem.getAmount() < sitem.getMaxStackSize()) {
				ItemStack newsitem = sitem.clone();
				newsitem.setAmount(sitem.getAmount()+1);
				ItemStack newcitem = null;
				if (citem.getAmount() > 1) {
					newcitem = citem.clone();
					newcitem.setAmount(citem.getAmount()-1);
				}
				cancelled = notifyClickedSlot(newsitem, sitem, inv, slot, cancelled, NOTIFY_CURSOR);
				cancelled = notifyCursor(newcitem, null, newsitem, sitem, inv, slot, cancelled);
			}
		} else if (click == ClickType.SHIFT_RIGHT || click == ClickType.SHIFT_LEFT) {
			cancelled = notifyClickedSlot(null, sitem, inv, slot, cancelled, NOTIFY_SHIFT);
			cancelled = notifyShifted(sitem, inv, slot, cancelled, -1);
		} else if (click == ClickType.NUMBER_KEY) {
			cancelled = notifyClickedSlot(null, sitem, inv, slot, cancelled, NOTIFY_SHIFT);
			cancelled = notifyShifted(sitem, inv, slot, cancelled, e.getHotbarButton());
		} else if (click == ClickType.CONTROL_DROP) {
			cancelled = notifyClickedSlot(null, sitem, inv, slot, cancelled, NOTIFY_DROP);
			cancelled = notifyDroped(sitem, null, inv, slot, cancelled);
		} else if (click == ClickType.DROP) {
			ItemStack newsitem = null;
			if (sitem.getAmount() > 1) {
				newsitem = sitem.clone();
				newsitem.setAmount(sitem.getAmount()-1);
			}
			ItemStack drop = sitem.clone();
			drop.setAmount(1);
			cancelled = notifyClickedSlot(newsitem, sitem, inv, slot, cancelled, NOTIFY_DROP);
			cancelled = notifyDroped(drop, newsitem, inv, slot, cancelled);
		} else if (click == ClickType.DOUBLE_CLICK) {
			cancelled = notifyDoubleClick(inv, slot, cancelled);
		}
		e.setCancelled(cancelled);
		return null;
	}
	
	protected abstract boolean notifyDoubleClick(Inventory inv, int slot, boolean cancelled);

	/**
	 * this method shows how the slot will look after the event
	 * @param item that will be in the slot after the event or null (may not be 100% correct on shift)
	 * the button won't check if there is a slot to shift the item to or a remaining amount in case of shifting
	 * @param inv
	 * @param slot
	 * @param cancelled the events cancel state
	 * @return true to cancel the click or false if not
	 * if you are not sure if you should cancel the event or not return the cancelled value
	 */
	protected abstract boolean notifyClickedSlot(ItemStack item, ItemStack olditem, Inventory inv, int slot, boolean cancelled, int state);
	
	/**
	 * this method show how the cursor will look after the event if the cursor has a change
	 * @param citem that will be on the cursor or null
	 * @param oldcitem the item that is currently on the cursor or null
	 * @param item the item that will be in the slot after the event
	 * @param olditem the item that is currently in the slot
	 * @param sourceinv
	 * @param sourceslot
	 * @param cancelled the events cancel state
	 * @return true to cancel the click or false if not
	 * if you are not sure if you should cancel the event or not return the cancelled value
	 */
	protected abstract boolean notifyCursor(ItemStack citem, ItemStack oldcitem, ItemStack item, ItemStack olditem, Inventory sourceinv, int sourceslot, boolean cancelled);
	
	/**
	 * 
	 * @param item that will be shifted
	 * @param sourceinv
	 * @param sourceslot
	 * @param cancelled the events cancel state
	 * @param hotbar number in range 0-8 or -1
	 * @return true to cancel the click or false if not
	 * if you are not sure if you should cancel the event or not return the cancelled value
	 */
	protected abstract boolean notifyShifted(ItemStack item, Inventory sourceinv, int sourceslot, boolean cancelled, int hotbar);

	/**
	 * 
	 * @param item that will be dropped
	 * @param slotitem the item that will be in the slot after the event
	 * @param sourceinv
	 * @param sourceslot
	 * @param cancelled the events cancel state
	 * @return true to cancel the click or false if not
	 * if you are not sure if you should cancel the event or not return the cancelled value
 	 */
	protected abstract boolean notifyDroped(ItemStack item, ItemStack slotitem, Inventory sourceinv, int sourceslot, boolean cancelled);
}
