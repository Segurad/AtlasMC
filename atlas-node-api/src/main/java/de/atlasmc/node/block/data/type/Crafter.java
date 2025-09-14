package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Orientable;
import de.atlasmc.node.block.data.Triggerable;

public interface Crafter extends Orientable, Triggerable {
	
	boolean isCrafting();
	
	void setCrafting(boolean crafting);
	
	Crafter clone();

}
