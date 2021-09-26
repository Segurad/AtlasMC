package de.atlasmc.block.tile;

import de.atlasmc.inventory.Inventory;

public interface Hopper extends AbstractContainerTile<Inventory> {
	
	public int getTransferCooldown();
	
	public void setTransferCooldown(int cooldown);

	public int getTransferAmount();

	/**
	 * Sets the max amount of items transfered each transfer
	 * @param amount
	 */
	public void setTransferAmount(int amount);

}
