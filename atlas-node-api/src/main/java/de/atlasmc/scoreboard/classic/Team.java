package de.atlasmc.scoreboard.classic;

import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.Chat;
import de.atlasmc.scoreboard.TeamOptionType;

public interface Team {

	public ChatColor getColor();
	
	public Chat getPrefix();
	
	public Chat getSuffix();
	
	public Chat getDisplayName();
	
	public String getName();
	
	public boolean getAllowFriedlyFire();
	
	public boolean canSeeInvisibleTeammeber();
	
	public void setPrefix(Chat prefix);
	
	public void setSuffix(Chat suffix);
	
	public void setDisplayName(Chat display);
	
	public void setColor(ChatColor color);
	
	public void setAllowFriedlyFire(boolean allow);
	
	public void setSeeInvisibleTeammeber(boolean see);
	
	public TeamOptionType getNameTagVisibility();
	
	public TeamOptionType getCollisionRule();
	
	public void setNameTagVisibility(TeamOptionType option);
	
	public void setCollisionRule(TeamOptionType option);
	
	public void unregister();
	
	public boolean isRegistered();
	
	public Scoreboard getScoreboard();
	
	public void update();

	public List<String> getEntries();
	
	public boolean addEntry(String entry);
	
	public boolean removeEntry(String entry);
	
	public boolean addEntry(List<String> entries);
	
	public boolean removeEntry(List<String> entries);

}
