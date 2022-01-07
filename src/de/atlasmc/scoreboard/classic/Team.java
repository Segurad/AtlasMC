package de.atlasmc.scoreboard.classic;

import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.scoreboard.TeamOptionType;

public interface Team {

	public ChatColor getColor();
	
	public ChatComponent getPrefix();
	
	public ChatComponent getSuffix();
	
	public ChatComponent getDisplayName();
	
	public String getName();
	
	public boolean getAllowFriedlyFire();
	
	public boolean canSeeInvisibleTeammeber();
	
	public void setPrefix(ChatComponent prefix);
	
	public void setSuffix(ChatComponent suffix);
	
	public void setDisplayName(ChatComponent display);
	
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
