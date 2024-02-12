package de.atlasmc.block.data.type;

import java.util.Set;

import de.atlasmc.block.data.Directional;

public interface ChiseledBookshelf extends Directional {
	
	int getMaxOccupiedSlots();
	
	Set<Integer> getOccupiedSots();
	
	boolean isSlotOccupied(int slot);
	
	void setSlotOccupied(int slot, boolean occupied);
	
	ChiseledBookshelf clone();

}
