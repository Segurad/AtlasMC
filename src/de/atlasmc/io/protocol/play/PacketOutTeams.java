package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.scoreboard.TeamOptionType;

@DefaultPacketID(PacketPlay.OUT_TEAMS)
public interface PacketOutTeams extends PacketPlay, PacketOutbound {
	
	public String getName();
	
	public Mode getMode();
	
	public ChatComponent getDisplayName();
	
	public boolean getAllowFriedlyFire();
	
	public boolean canSeeInvisibleTeammeber();
	
	public TeamOptionType getNameTagVisibility();
	
	public TeamOptionType getCollisionRule();
	
	public ChatColor getColor();
	
	public ChatComponent getPrefix();
	
	public ChatComponent getSuffix();
	
	public List<String> getEntities();
	
	public void setName(String name);
	
	public void setMode(Mode mode);
	
	public void setDisplayName(ChatComponent display);
	
	public void setPrefix(ChatComponent prefix);
	
	public void setSuffix(ChatComponent suffix);
	
	public void setNameTagVisibility(TeamOptionType option);
	
	public void setCollisionRule(TeamOptionType option);
	
	public void setColor(ChatColor color);
	
	public void setEntries(List<String> entries);
	
	public void setAllowFriedlyFire(boolean allow);
	
	public void setSeeInvisibleTeammeber(boolean see);
	
	@Override
	public default int getDefaultID() {
		return OUT_TEAMS;
	}
	
	public static enum Mode {
		CREATE_TEAM,
		REMOVE_TEAM,
		UPDATE_TEAM_INFO,
		ADD_ENTITIES,
		REMOVE_ENTITIES;
		
		public static Mode getByID(int id) {
			return values()[id];
		}
	}

}
