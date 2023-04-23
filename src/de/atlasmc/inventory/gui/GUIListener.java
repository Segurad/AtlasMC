package de.atlasmc.inventory.gui;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;

public interface GUIListener {

	void openedBy(Player player);
	
	void closedBy(Player player);
	
	void click(InventoryClickEvent event);
	
	void removed();
	
	/**
	 * Functional implementation of {@link GUIListener}
	 */
	@FunctionalInterface
	public interface GUIOpenListener extends GUIListener {
		
		void openedBy(Player player);
		
		@Override
		default void closedBy(Player player) {}
		
		@Override
		default void click(InventoryClickEvent event) {}
	
		@Override
		default void removed() {}
		
	}
	
	/**
	 * Functional implementation of {@link GUIListener}
	 */
	@FunctionalInterface
	public interface GUICloseListener extends GUIListener {
		
		@Override
		default void openedBy(Player player) {}
		
		void closedBy(Player player);
		
		@Override
		default void click(InventoryClickEvent event) {}
		
		@Override
		default void removed() {}
		
	}

	/**
	 * Functional implementation of {@link GUIListener}
	 */
	@FunctionalInterface
	public interface GUIClickListener extends GUIListener {
		
		@Override
		default void openedBy(Player player) {}
		
		@Override
		default void closedBy(Player player) {}
		
		@Override
		void click(InventoryClickEvent event);
	
		@Override
		default void removed() {}
		
	}

}
