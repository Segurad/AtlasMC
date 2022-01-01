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
import de.atlasmc.scoreboard.Team.OptionStatus;
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
			OptionStatus nameTagVisibility, OptionStatus collisionRule, ChatColor color, 
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
			out.writeByte(flags);
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
	public int getFlags() {
		return flags;
	}

	@Override
	public OptionStatus getNameTagVisibility() {
		switch (nameTagVisibility) {
		case "always":
			return OptionStatus.ALWAYS;
		case "hideForOtherTeams":
			return OptionStatus.FOR_OTHER_TEAMS;
		case "hideForOwnTeam":
			return OptionStatus.FOR_OWN_TEAM;
		case "never":
			return OptionStatus.NEVER;
		default:
			return OptionStatus.ALWAYS;
		}
	}

	@Override
	public OptionStatus getCollisionRule() {
		switch (collisionRule) {
		case "always":
			return OptionStatus.ALWAYS;
		case "pushOtherTeams":
			return OptionStatus.FOR_OTHER_TEAMS;
		case "pushOwnTeam":
			return OptionStatus.FOR_OWN_TEAM;
		case "never":
			return OptionStatus.NEVER;
		default:
			return OptionStatus.ALWAYS;
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

}
