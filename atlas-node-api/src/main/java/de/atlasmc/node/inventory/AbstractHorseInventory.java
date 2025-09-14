package de.atlasmc.node.inventory;

public interface AbstractHorseInventory extends Inventory {
	
	ItemStack getSaddle();
	
	void setSaddle(ItemStack saddle);

}
