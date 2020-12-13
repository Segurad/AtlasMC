package de.atlasmc.scoreboard.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.atlasmc.entity.Player;

public class ScoreboardManager {

	private final HashMap<Player, PlayerScoreboard> wrapper;
	private static final TabDisplayHandler defaultHandler;
	
	static {
		defaultHandler = new DefaultTabDisplayHandler();
	}
	
	public ScoreboardManager() {
		wrapper = new HashMap<>();
	}
	
	public PlayerScoreboard registerPlayer(Player player) {
		return registerPlayer(player, null);
	}
	
	public PlayerScoreboard registerPlayer(Player player, TabDisplayHandler comp) {
		if (wrapper.containsKey(player)) return wrapper.get(player);
		PlayerScoreboard board = new PlayerScoreboard(player, this, comp);
		wrapper.values().forEach(s -> s.getActiveTabComponent().add(board));
		wrapper.put(player, board);
		return board;
	}
	
	public PlayerScoreboard getScoreboard(Player player) {
		return wrapper.get(player);
	}
	
	public void unregisterPlayer(Player player) {
		if (wrapper.containsKey(player)) 
		wrapper.remove(player).unregister();
	}
	
	void unregister(Player player) {
		if (wrapper.containsKey(player))
		wrapper.remove(player);
	}

	public List<PlayerScoreboard> getScoreboards() {
		return new ArrayList<>(wrapper.values());
	}
	
	public final static TabDisplayHandler getDefaultTabDisplayHandler() {
		return defaultHandler;
	}
}
