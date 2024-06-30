package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.scoreboard.TeamOptionType;

@DefaultPacketID(PacketPlay.OUT_UPDATE_TEAMS)
public class PacketOutUpdateTeams extends AbstractPacket implements PacketPlayOut {
	
	private int flags;
	private ChatColor color;
	private String name;
	private Mode mode;
	private String prefix;
	private String suffix; 
	private String displayName;
	private TeamOptionType nameTagVisibility;
	private TeamOptionType collisionRule;
	private List<String> entities;
	
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
	
	public int getFlags() {
		return flags;
	}
	
	/**
	 * <ul>
	 * <li>0x01 - allow friendly fire</li>
	 * <li>0x02 - can see invisible players on teams</li>
	 * </ul>
	 * @param flags
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	public ChatColor getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public Mode getMode() {
		return mode;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getDisplayName() {
		return displayName;
	}

	public TeamOptionType getNameTagVisibility() {
		return nameTagVisibility;
	}

	public TeamOptionType getCollisionRule() {
		return collisionRule;
	}

	public List<String> getEntities() {
		return entities;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setNameTagVisibility(TeamOptionType nameTagVisibility) {
		this.nameTagVisibility = nameTagVisibility;
	}

	public void setCollisionRule(TeamOptionType collisionRule) {
		this.collisionRule = collisionRule;
	}

	public void setEntities(List<String> entities) {
		this.entities = entities;
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
