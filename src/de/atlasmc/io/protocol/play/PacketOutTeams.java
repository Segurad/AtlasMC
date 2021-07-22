package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.scoreboard.Team.OptionStatus;

@DefaultPacketID(PacketPlay.OUT_TEAMS)
public interface PacketOutTeams extends PacketPlay, PacketOutbound {
	
	public String getName();
	public Mode getMode();
	public ChatComponent getDisplayName();
	public int getFlags();
	public OptionStatus getNameTagVisibility();
	public OptionStatus getCollisionRule();
	public ChatColor getColor();
	public ChatComponent getPrefix();
	public ChatComponent getSuffix();
	public List<String> getEntities();
	
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
