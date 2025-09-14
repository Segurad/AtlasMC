package de.atlasmc.node.scoreboard.classic;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.scoreboard.DisplaySlot;
import de.atlasmc.node.scoreboard.RenderType;
import de.atlasmc.node.scoreboard.ScoreboardView;

public interface Scoreboard {
	
	/**
	 * Creates new view for the Player
	 * @param player
	 * @param apply if the view should be applied to the player
	 * @return the view
	 */
	ClassicScoreboardView createView(Player player, boolean apply);
	
	/**
	 * Returns a List of all current viewers
	 * @return list of viewers
	 */
	List<ScoreboardView> getViewers();
	
	Objective getObjective(String name);

	Objective getObjective(DisplaySlot slot);
	
	List<Objective> getObjectives();
	
	Objective registerNewObjective(String name);
	
	Objective registerNewObjective(String name, Chat displayName);
	
	Objective registerNewObjective(String name, Chat displayName, RenderType renderType);
	
	/**
	 * Registers a new Team for this Scoreboard.<br>
	 * Will return the already registered Team if existing
	 * @param name
	 * @return new team or registered team
	 */
	Team registerNewTeam(String name);
	
	List<Team> getTeams();
	
	Team getTeam(String name);
	
	void resetScores(String entry);
	
	void resetScores();
	
}
