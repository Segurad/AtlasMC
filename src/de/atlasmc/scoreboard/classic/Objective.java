package de.atlasmc.scoreboard.classic;

import java.util.List;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.scoreboard.DisplaySlot;
import de.atlasmc.scoreboard.RenderType;

public interface Objective {
	
	public Scoreboard getScoreboard();
	
	public Score getScore(String entry);
	
	public String getName();
	
	public RenderType getRenderType();

	public void setRenderType(RenderType renderType);
	
	public ChatComponent getDisplayName();
	
	public void setDisplayName(ChatComponent chat);
	
	public void unregister();
	
	public boolean isRegistered();
	
	public DisplaySlot getDisplaySlot();
	
	public void setDisplaySlot(DisplaySlot slot);

	public boolean hasScores();

	public List<Score> getScores();
	
	public void update();

	public boolean resetScore(String entry);
	
	public boolean hasScore(String entry);
	
}
