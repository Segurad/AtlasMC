package de.atlasmc.inventory.gui;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;

public interface GUIListener {

	public void openedBy(Player player);
	public void closedBy(Player player);
	public void click(InventoryClickEvent event);
	
	/**
	 * Functional implementation of {@link GUIListener}
	 */
	@FunctionalInterface
	public interface GUIOpenListener extends GUIListener {
		
		public void openedBy(Player player);
		public default void closedBy(Player player) {}
		public default void click(InventoryClickEvent event) {}
	}
	
	/**
	 * Functional implementation of {@link GUIListener}
	 */
	@FunctionalInterface
	public interface GUICloseListener extends GUIListener {
		
		public default void openedBy(Player player) {}
		public void closedBy(Player player);
		public default void click(InventoryClickEvent event) {}
	}

	/**
	 * Functional implementation of {@link GUIListener}
	 */
	@FunctionalInterface
	public interface GUIClickListener extends GUIListener {
		
		public default void openedBy(Player player) {}
		public default void closedBy(Player player) {}
		public void click(InventoryClickEvent event);
	}
}
