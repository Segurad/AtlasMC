package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutTeams;
import de.atlasmc.scoreboard.TeamOptionType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTeams extends AbstractPacket implements PacketOutTeams {

	private int flags, color;
	private String name;
	private int mode;
	private String prefix, suffix, displayName;
	private String nameTagVisibility, collisionRule;
	private List<String> entities;
	
	public CorePacketOutTeams() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutTeams(String name, Mode mode, ChatComponent displayName, int flags, 
			TeamOptionType nameTagVisibility, TeamOptionType collisionRule, ChatColor color, 
			ChatComponent prefix, ChatComponent suffix, List<String> entities) {
		this();
		this.flags = flags;
		this.name = name;
		this.displayName = displayName.getJsonText();
		this.prefix = prefix.getJsonText();
		this.suffix = suffix.getJsonText();
		this.color = color.getID();
		this.entities = entities;
		this.mode = mode.ordinal();
		
		switch (nameTagVisibility) {
		case ALWAYS: this.nameTagVisibility = "always";
			break;
		case FOR_OTHER_TEAMS: this.nameTagVisibility = "hideForOtherTeams";
			break;
		case FOR_OWN_TEAM: this.nameTagVisibility = "hideForOwnTeam";
			break;
		case NEVER: this.nameTagVisibility = "never";
			break;
		default: this.nameTagVisibility = "always";
			break;
		}
		switch (collisionRule) {
		case ALWAYS: this.nameTagVisibility = "always";
			break;
		case FOR_OTHER_TEAMS: this.nameTagVisibility = "pushOtherTeams";
			break;
		case FOR_OWN_TEAM: this.nameTagVisibility = "pushOwnTeam";
			break;
		case NEVER: this.nameTagVisibility = "never";
			break;
		default: this.nameTagVisibility = "always";
			break;
		}
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		name = readString(in);
		mode = in.readByte();
		switch (mode) {
		case 0: 
			displayName = readString(in);
			flags = in.readByte();
			nameTagVisibility = readString(in);
			collisionRule = readString(in);
			color = readVarInt(in);
			prefix = readString(in);
			suffix = readString(in);
			int size = readVarInt(in);
			entities = new ArrayList<String>(size);
			for (int i = 0; i < size; i++) {
				entities.add(readString(in));
			}
			break;
		case 1:
			break;
		case 2:
			displayName = readString(in);
			flags = in.readByte();
			nameTagVisibility = readString(in);
			collisionRule = readString(in);
			color = readVarInt(in);
			prefix = readString(in);
			suffix = readString(in);
			break;
		case 3:
		case 4:
			size = readVarInt(in);
			entities = new ArrayList<String>(size);
			for (int i = 0; i < size; i++) {
				entities.add(readString(in));
			}
			break;
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(name, out);
		out.writeByte(mode);
		switch (mode) {
		case 0:
			writeString(displayName, out);
			out.writeByte(flags & 0x03);
			writeString(nameTagVisibility, out);
			writeString(collisionRule, out);
			writeVarInt(color, out);
			writeString(prefix, out);
			writeString(suffix, out);
			if (entities == null) {
				writeVarInt(0, out);
				return;
			}
			writeVarInt(entities.size(), out);
			for (String s : entities) {
				writeString(s, out);
			}
			break;
		case 1:
			break;
		case 2:
		case 3:
			writeVarInt(entities.size(), out);
			for (String s : entities) {
				writeString(s, out);
			}
			break;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Mode getMode() {
		return Mode.getByID(mode);
	}

	@Override
	public ChatComponent getDisplayName() {
		return ChatUtil.toChat(displayName);
	}

	@Override
	public TeamOptionType getNameTagVisibility() {
		switch (nameTagVisibility) {
		case "always":
			return TeamOptionType.ALWAYS;
		case "hideForOtherTeams":
			return TeamOptionType.FOR_OTHER_TEAMS;
		case "hideForOwnTeam":
			return TeamOptionType.FOR_OWN_TEAM;
		case "never":
			return TeamOptionType.NEVER;
		default:
			return TeamOptionType.ALWAYS;
		}
	}

	@Override
	public TeamOptionType getCollisionRule() {
		switch (collisionRule) {
		case "always":
			return TeamOptionType.ALWAYS;
		case "pushOtherTeams":
			return TeamOptionType.FOR_OTHER_TEAMS;
		case "pushOwnTeam":
			return TeamOptionType.FOR_OWN_TEAM;
		case "never":
			return TeamOptionType.NEVER;
		default:
			return TeamOptionType.ALWAYS;
		}
	}

	@Override
	public ChatColor getColor() {
		return ChatColor.getByID(color);
	}

	@Override
	public ChatComponent getPrefix() {
		return  ChatUtil.toChat(prefix);
	}

	@Override
	public ChatComponent getSuffix() {
		return ChatUtil.toChat(suffix);
	}

	@Override
	public List<String> getEntities() {
		return entities;
	}

	@Override
	public boolean getAllowFriedlyFire() {
		return (flags & 0x01) == 0x01;
	}

	@Override
	public boolean canSeeInvisibleTeammeber() {
		return (flags & 0x02) == 0x02;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setMode(Mode mode) {
		this.mode = mode.ordinal();
	}

	@Override
	public void setDisplayName(ChatComponent display) {
		this.displayName = display.getJsonText();
	}

	@Override
	public void setPrefix(ChatComponent prefix) {
		this.prefix = prefix.getJsonText();
	}

	@Override
	public void setSuffix(ChatComponent suffix) {
		this.suffix = suffix.getJsonText();
	}

	@Override
	public void setNameTagVisibility(TeamOptionType option) {
		switch (option) {
		case ALWAYS:
			nameTagVisibility = "always";
			break;
		case FOR_OTHER_TEAMS:
			nameTagVisibility = "hideForOwnTeam";
			break;
		case FOR_OWN_TEAM:
			nameTagVisibility = "hideForOtherTeam";
			break;
		case NEVER:
			nameTagVisibility = "never";
			break;
		}
	}

	@Override
	public void setCollisionRule(TeamOptionType option) {
		switch (option) {
		case ALWAYS:
			nameTagVisibility = "always";
			break;
		case FOR_OTHER_TEAMS:
			nameTagVisibility = "pushOtherTeam";
			break;
		case FOR_OWN_TEAM:
			nameTagVisibility = "pushOwnTeam";
			break;
		case NEVER:
			nameTagVisibility = "never";
			break;
		}
	}

	@Override
	public void setColor(ChatColor color) {
		this.color = color.getID();
	}

	@Override
	public void setEntries(List<String> entries) {
		this.entities = entries;
	}

	@Override
	public void setAllowFriedlyFire(boolean allow) {
		if (allow)
			flags |= 0x01;
		else
			flags &= 0xFE;
	}

	@Override
	public void setSeeInvisibleTeammeber(boolean see) {
		if (see)
			flags |= 0x02;
		else
			flags &= 0xFD;
	}

}
