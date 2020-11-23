package de.atlasmc.inventory.gui;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.ItemStack;

public interface Button {
	
	/**
	 * 
	 * @param e
	 * @return a ItemStack which will be set in the clicked slot or null for no changes
	 */
	public ItemStack press(InventoryClickEvent e);

	public ItemStack getIcon();
	public boolean hasIcon();
	public ItemStack getIcon(int slot);

	public String getPermission();

	public void setPermission(String permission);

	public boolean hasPermission();

	/**
	 * this method will be called if a player has not the needed permission
	 * 
	 * @param player the player who clicked
	 */
	public void unauthorizedClick(Player player);

	public Button clone();
}
