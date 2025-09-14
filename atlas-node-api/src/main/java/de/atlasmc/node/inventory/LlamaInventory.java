package de.atlasmc.node.inventory;

import de.atlasmc.node.entity.Llama;

/**
 * Inventory of a {@link Llama}
 */
public interface LlamaInventory extends AbstractHorseInventory {
	
	ItemStack getDecor();
	
	void setDecor(ItemStack decor);

}
