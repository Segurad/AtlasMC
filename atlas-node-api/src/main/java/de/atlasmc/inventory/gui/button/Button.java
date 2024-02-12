package de.atlasmc.inventory.gui.button;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.GUI;
import de.atlasmc.util.annotation.InternalAPI;

/**
 * A Button is a clickable element within a {@link GUI} to add functionality
 */
public interface Button {
	
	/**
	 * Called by the GUI when this Button is clicked.
	 * Returning a ItemStack to change the click slot or null for no changes
	 * @param event the click event
	 * @return item or null
	 */
	@InternalAPI
	ItemStack press(InventoryClickEvent event);

	ItemStack getIcon();
	
	boolean hasIcon();
	
	ItemStack getIcon(int slot);

	/**
	 * Returns the Permission of this Button
	 * @return permission
	 */
	String getPermission();

	/**
	 * Sets a Permission for this Button
	 * @param permission
	 */
	void setPermission(String permission);

	/**
	 * Returns whether or not this Button has a Permission
	 * @return true if a permission is present
	 */
	boolean hasPermission();

	/**
	 * Called by the GUI if the Button is clicked by a player without having the required permission
	 * @param player the player who clicked
	 */
	@InternalAPI
	void unauthorizedClick(Player player);

	/**
	 * Returns a copy
	 * @return button
	 */
	Button clone();
}
