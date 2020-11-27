package de.atlasmc.scoreboard.atlas;

import java.util.HashMap;

import de.atlasmc.chat.ChatColor;

/**
 * 
 * @author Segurad
 *
 */
public interface TabDisplayHandler {

	/**
	 * A Group id for the Player
	 * a 4 digit sort key + a String with max length of 12 normal the first part of the players name
	 * @param board
	 * @return
	 */
	public String getPlayerGroupNameID(PlayerScoreboard board);
	
	/**
	 * String of with a max length of 12
	 * @param board
	 * @return
	 */
	public String getGroupName(PlayerScoreboard board);
	
	/**
	 * A 4 digit sort key containing 0-9 & a-z
	 * @param board
	 * @return
	 */
	public String getGroupID(PlayerScoreboard board);
	
	public ChatColor getColor(PlayerScoreboard board);
	public String getPrefix(PlayerScoreboard board);
	public String getSuffix(PlayerScoreboard board);
	
	public HashMap<Option, OptionStatus> getOptions(PlayerScoreboard board);
}
