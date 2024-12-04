package de.atlasmc.scoreboard.classic;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.scoreboard.DisplaySlot;
import de.atlasmc.scoreboard.RenderType;

public interface Objective {
	
	Scoreboard getScoreboard();
	
	Score getScore(String name);
	
	String getName();
	
	RenderType getRenderType();

	void setRenderType(RenderType renderType);
	
	Chat getDisplayName();
	
	void setDisplayName(Chat chat);
	
	void unregister();
	
	boolean isRegistered();
	
	DisplaySlot getDisplaySlot();
	
	void setDisplaySlot(DisplaySlot slot);

	boolean hasScores();

	List<Score> getScores();
	
	void update();

	boolean resetScore(String name);
	
	boolean hasScore(String name);
	
}
