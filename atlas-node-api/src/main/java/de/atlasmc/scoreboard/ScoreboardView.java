package de.atlasmc.scoreboard;

import de.atlasmc.entity.Player;

public interface ScoreboardView {
	
	/**
	 * Returns the Player this view belongs to
	 * @return player
	 */
	Player getViewer();
	
	/**
	 * Returns the handler of this view
	 * @return handler
	 */
	Object getHandler();
	
	/**
	 * Called when the view is removed from the player
	 */
	void remove();
	
	/**
	 * Called when the view is set to the player
	 */
	void add();

}
