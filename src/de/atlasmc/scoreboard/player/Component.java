package de.atlasmc.scoreboard.player;

public interface Component {

	public void update();
	/**
	 * if the component is active the next component will become active
	 */
	public void unregister();
	public boolean isActive();
	public void setActiv(boolean value);
	public boolean isRegistered();
	public PlayerScoreboard getScoreboard();
}
