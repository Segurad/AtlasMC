package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.InventoryType.SlotType;

public class CoreInventoryView implements InventoryView {
	
	private final Player player;
	private Inventory bottom;
	private Inventory top;
	private int viewID;
	
	public CoreInventoryView(Player player, Inventory bottom, Inventory top, int viewID) {
		this.player = player;
		this.bottom = bottom;
		this.top = top;
		this.viewID = viewID;
	}
	
	public void close() {
		player.closeInventory();
	}
	
	/**
	 * Return the type of the top Inventory
	 * @return
	 */
	public InventoryType getType() {
		return top.getType();
	}
	
	/**
	 * 
	 * @return the owner of this view
	 */
	public Player getPlayer() {
		return player;
	}
	
	public Inventory getTopInventory() {
		return top;
	}
	
	public void setTopInventory(Inventory inv) {
		this.top = inv;
	}
	
	public Inventory getBottomInventory() {
		return bottom;
	}
	
	public void setCursor(ItemStack item) {
		player.setItemOnCursor(item);
	}
	
	public ItemStack getCursor() {
		return player.getItemOnCursor();
	}
	
	@Override
	public void setCursorUnsafe(ItemStack item) {
		player.setItemOnCursorUnsafe(item);
	}

	@Override
	public ItemStack getCursorUnsafe() {
		return player.getItemOnCursorUnsafe();
	}

	public ItemStack getItem(int rawSlot) {
		Inventory inv = getInventory(rawSlot);
		return inv == null ? null : inv.getItem(convertSlot(rawSlot));
	}
	
	public void setItem(int rawSlot, ItemStack item) {
		Inventory inv = getInventory(rawSlot);
		if (inv != null) 
			inv.setItem(convertSlot(rawSlot), item);
	}
	
	@Override
	public ItemStack getItemUnsafe(int rawSlot) {
		Inventory inv = getInventory(rawSlot);
		return inv == null ? null : inv.getItemUnsafe(convertSlot(rawSlot));
	}

	@Override
	public void setItemUnsafe(int rawSlot, ItemStack item) {
		Inventory inv = getInventory(rawSlot);
		if (inv != null)
			inv.setItemUnsafe(convertSlot(rawSlot), item);
	}
	
	public int countSlots() {
		return top.getSize() + bottom.getSize();
	}
	
	public int convertSlot(int rawSlot) {
		int numInTop = getTopInventory().getSize();
        // Index from the top inventory as having slots from [0,size]
        if (rawSlot < numInTop) {
            return rawSlot;
        }

        // Move down the slot index by the top size
        int slot = rawSlot - numInTop;

        // Player crafting slots are indexed differently. The matrix is caught by the first return.
        // Creative mode is the same, except that you can't see the crafting slots (but the IDs are still used)
        if (getType() == InventoryType.CRAFTING) {
            /*
             * Raw Slots:
             *
             * 5             1  2     0
             * 6             3  4
             * 7
             * 8           45
             * 9  10 11 12 13 14 15 16 17
             * 18 19 20 21 22 23 24 25 26
             * 27 28 29 30 31 32 33 34 35
             * 36 37 38 39 40 41 42 43 44
             */

            /*
             * Converted Slots:
             *
             * 39             1  2     0
             * 38             3  4
             * 37
             * 36          40
             * 9  10 11 12 13 14 15 16 17
             * 18 19 20 21 22 23 24 25 26
             * 27 28 29 30 31 32 33 34 35
             * 0  1  2  3  4  5  6  7  8
             */

            if (slot < 4) {
                // Send [5,8] to [39,36]
                return 39 - slot;
            } else if (slot > 39) {
                // Slot lives in the extra slot section
                return slot;
            } else {
                // Reset index so 9 -> 0
                slot -= 4;
            }
        }

        // 27 = 36 - 9
        if (slot >= 27) {
            // Put into hotbar section
            slot -= 27;
        } else {
            // Take out of hotbar section
            // 9 = 36 - 27
            slot += 9;
        }

        return slot;
	}
	
	/**
	 * Converts from the normal slot to the raw slot
	 * @param inv
	 * @param slot
	 * @return
	 */
	public int convertSlot(Inventory inv, int slot) {
		if (inv == getBottomInventory()) {
			if (slot < 0 || slot > 40) throw new ArrayIndexOutOfBoundsException("Slot out of Bounds!");
			if (slot <= 8) { // Move hotbar to end
				slot+=27;
			} else if (slot > 35 && slot <= 39) { // move armor to start
				slot = 4 - (slot - 36);
			}
			return slot + getTopInventory().getSize();
		} if (inv == getTopInventory()) {
			final int numInTop = getTopInventory().getSize();
			// Index from the top inventory as having slots from [0,size]
        	if (slot < numInTop) {
            	return slot;
        	} else throw new ArrayIndexOutOfBoundsException("Slot out of Bounds!");
		} else
			throw new IllegalArgumentException("Inventory does not belong to this InventoryView!");
	}
	
	public SlotType getSlotType(int rawSlot) {
		Inventory inv = getInventory(rawSlot);
		if (inv == null) 
			return SlotType.OUTSIDE;
		return inv.getSlotType(convertSlot(rawSlot));
	}
	
	public Inventory getInventory(int rawSlot) {
		if (rawSlot == -999 || rawSlot == -1) {
			return null;
		}
		if (rawSlot < 0 || rawSlot > countSlots()) 
			throw new IllegalArgumentException("Unknown Slot: " + rawSlot);
		if (rawSlot < top.getSize()) 
			return top;
		return bottom;
	}
	
	public Chat getTitle() {
		return top.getTitle();
	}
	
	public int getViewID() {
		return viewID;
	}
	
	public void setViewID(int viewID) {
		this.viewID = viewID;
	}

}
