package de.atlasmc.inventory;

import de.atlasmc.entity.Llama;

/**
 * Inventory of a {@link Llama}
 */
public interface LlamaInventory extends AbstractHorseInventory {
	
	ItemStack getDecor();
	
	void setDecor(ItemStack decor);

}
