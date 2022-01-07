package de.atlasmc.scoreboard;

import de.atlasmc.entity.Player;

public interface ScoreboardView {
	
	/**
	 * Returns the Player this view belongs to
	 * @return player
	 */
	public Player getViewer();
	
	/**
	 * Returns the handler of this view
	 * @return handler
	 */
	public Object getHandler();
	
	/**
	 * Called when the view is removed from the player
	 */
	public void remove();
	
	/**
	 * Called when the view is set to the player
	 */
	public void add();

}
