package de.atlasmc.node.scoreboard.classic;

import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.node.scoreboard.TeamOptionType;
import de.atlasmc.chat.Chat;

public interface Team {

	ChatColor getColor();
	
	Chat getPrefix();
	
	Chat getSuffix();
	
	Chat getDisplayName();
	
	String getName();
	
	boolean getAllowFriedlyFire();
	
	boolean canSeeInvisibleTeammeber();
	
	void setPrefix(Chat prefix);
	
	void setSuffix(Chat suffix);
	
	void setDisplayName(Chat display);
	
	void setColor(ChatColor color);
	
	void setAllowFriedlyFire(boolean allow);
	
	void setSeeInvisibleTeammeber(boolean see);
	
	TeamOptionType getNameTagVisibility();
	
	TeamOptionType getCollisionRule();
	
	void setNameTagVisibility(TeamOptionType option);
	
	void setCollisionRule(TeamOptionType option);
	
	void unregister();
	
	boolean isRegistered();
	
	Scoreboard getScoreboard();
	
	void update();

	List<String> getEntries();
	
	boolean addEntry(String entry);
	
	boolean removeEntry(String entry);
	
	boolean addEntry(List<String> entries);
	
	boolean removeEntry(List<String> entries);

}
