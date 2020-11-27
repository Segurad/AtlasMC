package de.atlasmc.scoreboard;

import de.atlasmc.chat.ChatColor;

public interface Team {

	public ChatColor getColor();
	public String getPrefix();
	public String getSuffix();
	public String getName();
	public void setPrefix(String prefix);
	public void setSuffix(String suffix);
	public void setColor(ChatColor color);
	public void unregister();

}
