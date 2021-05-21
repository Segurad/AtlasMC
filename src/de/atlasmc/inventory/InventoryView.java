package de.atlasmc.inventory;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.util.Validate;

public class InventoryView {
	
	protected Player player;
	protected Inventory bottom, top;
	protected InventoryType type;
	
	public InventoryView(Player player, Inventory bottom, Inventory top, InventoryType type) {
		this.player = player;
		this.bottom = bottom;
		this.top = top;
		this.type = type;
	}
	
	public void close() {
		player.closeInventory();
	}
	
	public InventoryType getType() {
		return type;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Inventory getTopInventory() {
		return top;
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

	public ItemStack getItem(int rawSlot) {
		Inventory inv = getInventory(rawSlot);
		return inv == null ? null : inv.getItem(convertSlot(rawSlot));
	}
	
	public void setItem(int rawSlot, ItemStack item) {
		Inventory inv = getInventory(rawSlot);
		if (inv != null) inv.setItem(convertSlot(rawSlot), item);
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
        if (getType() == InventoryType.CRAFTING || getType() == InventoryType.CREATIVE) {
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
			
		} if (inv == getTopInventory()) {
			int numInTop = getTopInventory().getSize();
			// Index from the top inventory as having slots from [0,size]
        	if (slot < numInTop) {
            	return slot;
        	} else throw new ArrayIndexOutOfBoundsException("Slot out of Bounds!");
		} else
			throw new IllegalArgumentException("Inventory does not belong to this InventoryView!");
	}
	
	public SlotType getSlotType(int rawSlot) {
		Inventory inv = getInventory(rawSlot);
		if (inv == null) return SlotType.OUTSIDE;
		return inv.getSlotType(convertSlot(rawSlot));
	}
	
	public Inventory getInventory(int rawSlot) {
		if (rawSlot == -999 || rawSlot == -1) {
			return null;
		}
		Validate.isTrue(rawSlot >= 0 && rawSlot < countSlots(), "Unknown Slot: " + rawSlot);
		if (rawSlot < top.getSize()) return top;
		return bottom;
	}
	
	public String getTitle() {
		return top.getTitle();
	}
}
