package de.atlasmc.inventory.gui;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.util.annotation.InternalAPI;

public interface GUIListener {

	void openedBy(Player player);
	
	void closedBy(Player player);
	
	void click(InventoryClickEvent event);
	
	/**
	 * Called by the GUI when this listener gets removed
	 */
	@InternalAPI
	void removed();
	
	/**
	 * Functional implementation of {@link GUIListener}
	 */
	@FunctionalInterface
	static interface GUIOpenListener extends GUIListener {
		
		void openedBy(Player player);
		
		@Override
		default void closedBy(Player player) { 
			// not required 
		}
		
		@Override
		default void click(InventoryClickEvent event) {}
	
		@Override
		default void removed() { 
			// not required 
		}
		
	}
	
	/**
	 * Functional implementation of {@link GUIListener}
	 */
	@FunctionalInterface
	static interface GUICloseListener extends GUIListener {
		
		@Override
		default void openedBy(Player player) {
			// not required
		}
		
		void closedBy(Player player);
		
		@Override
		default void click(InventoryClickEvent event) {
			// not required
		}
		
		@Override
		default void removed() {
			// not required
		}
		
	}

	/**
	 * Functional implementation of {@link GUIListener}
	 */
	@FunctionalInterface
	static interface GUIClickListener extends GUIListener {
		
		@Override
		default void openedBy(Player player) {
			// not required
		}
		
		@Override
		default void closedBy(Player player) {
			// not required
		}
		
		@Override
		void click(InventoryClickEvent event);
	
		@Override
		default void removed() {
			// not required
		}
		
	}

}
