package de.atlasmc.chat;

import java.util.List;

import de.atlasmc.entity.Player;

public interface ChatChannel {
	
	public void sendMessage(Chat message);
	
	public void sendMessage(Chat message, ChatType type);
	
	public List<Player> getPlayers();
	
	public void addPlayer(Player player);
	
	public void removePlayer(Player player);
	
	public String getName();
	
	/**
	 * 
	 * @return the prefix or null
	 */
	public Chat getPrefix();

}
