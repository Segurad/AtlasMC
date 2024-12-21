package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Orientable;
import de.atlasmc.block.data.Triggerable;

public interface Crafter extends Orientable, Triggerable {
	
	boolean isCrafting();
	
	void setCrafting(boolean crafting);
	
	Crafter clone();

}
