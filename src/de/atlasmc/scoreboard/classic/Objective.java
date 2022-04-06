package de.atlasmc.scoreboard.classic;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.scoreboard.DisplaySlot;
import de.atlasmc.scoreboard.RenderType;

public interface Objective {
	
	public Scoreboard getScoreboard();
	
	public Score getScore(String name);
	
	public String getName();
	
	public RenderType getRenderType();

	public void setRenderType(RenderType renderType);
	
	public Chat getDisplayName();
	
	public void setDisplayName(Chat chat);
	
	public void unregister();
	
	public boolean isRegistered();
	
	public DisplaySlot getDisplaySlot();
	
	public void setDisplaySlot(DisplaySlot slot);

	public boolean hasScores();

	public List<Score> getScores();
	
	public void update();

	public boolean resetScore(String name);
	
	public boolean hasScore(String name);
	
}
