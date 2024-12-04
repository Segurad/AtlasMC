package de.atlasmc.inventory;

public interface AbstractHorseInventory extends Inventory {
	
	ItemStack getSaddle();
	
	void setSaddle(ItemStack saddle);

}
