package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.scoreboard.TeamOptionType;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_TEAMS, definition = "set_player_team")
public class PacketOutUpdateTeams extends AbstractPacket implements PacketPlayOut {
	
	public String name;
	public Mode mode;
	/**
	 * <ul>
	 * <li>0x01 - allow friendly fire</li>
	 * <li>0x02 - can see invisible players on teams</li>
	 * </ul>
	 * @param flags
	 */
	public int flags;
	public ChatColor color;
	public Chat prefix;
	public Chat suffix; 
	public Chat displayName;
	public TeamOptionType nameTagVisibility;
	public TeamOptionType collisionRule;
	public List<String> entities;
	
	public boolean getAllowFriedlyFire() {
		return (flags & 0x1) == 0x1;
	}
	
	public boolean canSeeInvisibleTeammeber() {
		return (flags & 0x2) == 0x2;
	}
	
	public void setAllowFriedlyFire(boolean allow) {
		if (allow)
			flags |= 0x1;
		else
			flags &= 0xFE;
	}
	
	public void setSeeInvisibleTeammeber(boolean see) {
		if (see)
			flags |= 0x2;
		else
			flags &= 0xFD;
	}

	@Override
	public int getDefaultID() {
		return OUT_UPDATE_TEAMS;
	}
	
	public static enum Mode {
		CREATE_TEAM,
		REMOVE_TEAM,
		UPDATE_TEAM_INFO,
		ADD_ENTITIES,
		REMOVE_ENTITIES;
		
		private static List<Mode> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Mode getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Mode> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
