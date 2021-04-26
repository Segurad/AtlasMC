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
	
	public static enum OptionStatus {
		ALWAYS,
		FOR_OTHER_TEAMS,
		FOR_OWN_TEAM,
		NEVER;
		
		public static OptionStatus getByID(int id) {
			return values()[id];
		}
	}

}
