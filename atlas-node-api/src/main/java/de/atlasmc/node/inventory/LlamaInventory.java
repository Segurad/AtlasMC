package de.atlasmc.node.inventory;

/**
 * Inventory of a Llama entity
 */
public interface LlamaInventory extends AbstractHorseInventory {
	
	ItemStack getDecor();
	
	void setDecor(ItemStack decor);

}
