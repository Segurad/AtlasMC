package de.atlasmc.block.tile;

import de.atlasmc.inventory.Inventory;
import it.unimi.dsi.fastutil.ints.IntSet;

public interface Crafter extends AbstractContainerTile<Inventory> {
	
	int getCraftingTicksRemaining();
	
	void setCraftingticksRemaining(int ticks);
	
	IntSet getDisabledSlots();
	
	boolean hasDisabledSlots();
	
	boolean isSlotDisabled(int slot);
	
	void setSlotDisabled(int slot, boolean disabled);
	
	boolean isTriggered();
	
	void setTriggered(boolean triggered);

}
