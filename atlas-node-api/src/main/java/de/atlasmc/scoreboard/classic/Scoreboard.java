package de.atlasmc.scoreboard.classic;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.scoreboard.DisplaySlot;
import de.atlasmc.scoreboard.RenderType;
import de.atlasmc.scoreboard.ScoreboardView;

public interface Scoreboard {
	
	/**
	 * Creates new view for the Player
	 * @param player
	 * @param apply if the view should be applied to the player
	 * @return the view
	 */
	public ScoreboardView createView(Player player, boolean apply);
	
	/**
	 * Returns a List of all current viewers
	 * @return list of viewers
	 */
	public List<ScoreboardView> getViewers();
	
	public Objective getObjective(String name);

	public Objective getObjective(DisplaySlot slot);
	
	public List<Objective> getObjectives();
	
	public Objective registerNewObjective(String name);
	
	public Objective registerNewObjective(String name, Chat displayName);
	
	public Objective registerNewObjective(String name, Chat displayName, RenderType renderType);
	
	/**
	 * Registers a new Team for this Scoreboard.<br>
	 * Will return the already registered Team if existing
	 * @param name
	 * @return new team or registered team
	 */
	public Team registerNewTeam(String name);
	
	public List<Team> getTeams();
	
	public Team getTeam(String name);
	
	public void resetScores(String entry);
	
	public void resetScores();
	
}
