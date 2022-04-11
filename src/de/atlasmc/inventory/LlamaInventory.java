package de.atlasmc.inventory;

import de.atlasmc.entity.Llama;

/**
 * Inventory of a {@link Llama}
 */
public interface LlamaInventory extends AbstractHorseInventory {
	
	public ItemStack getDecor();
	
	public void setDecor(ItemStack decor);

}
