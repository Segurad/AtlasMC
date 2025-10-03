package de.atlasmc.node.scoreboard;

import de.atlasmc.node.entity.Player;
import de.atlasmc.util.annotation.NotNull;

/**
 * ScoreboardView used as simple interface for various implementations
 */
public interface ScoreboardView {
	
	/**
	 * Returns the Player this view belongs to
	 * @return player
	 */
	@NotNull
	Player getViewer();
	
	/**
	 * Returns the handler of this view
	 * @return handler
	 */
	@NotNull
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
