package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.chat.ChatColor;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutTeams;
import de.atlasmc.io.protocol.play.PacketOutTeams.Mode;
import de.atlasmc.scoreboard.TeamOptionType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTeams extends PacketIO<PacketOutTeams> {

	@Override
	public void read(PacketOutTeams packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setName(readString(in));
		Mode mode = Mode.getByID(in.readByte());
		packet.setMode(mode);
		switch (mode) {
		case CREATE_TEAM: 
			packet.setDisplayName(readString(in));
			packet.setFlags(in.readByte());
			packet.setNameTagVisibility(getNameTagVisibility(readString(in)));
			packet.setCollisionRule(getCollisionRule(readString(in)));
			packet.setColor(ChatColor.getByID(readVarInt(in)));
			packet.setPrefix(readString(in));
			packet.setSuffix(readString(in));
			int size = readVarInt(in);
			List<String> entities = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				entities.add(readString(in));
			}
			packet.setEntities(entities);
			break;
		case REMOVE_TEAM:
			break;
		case UPDATE_TEAM_INFO:
			packet.setDisplayName(readString(in));
			packet.setFlags(in.readByte());
			packet.setNameTagVisibility(getNameTagVisibility(readString(in)));
			packet.setCollisionRule(getCollisionRule(readString(in)));
			packet.setColor(ChatColor.getByID(readVarInt(in)));
			packet.setPrefix(readString(in));
			packet.setSuffix(readString(in));
			break;
		case ADD_ENTITIES:
		case REMOVE_ENTITIES:
			size = readVarInt(in);
			entities = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				entities.add(readString(in));
			}
			packet.setEntities(entities);
			break;
		}
	}

	@Override
	public void write(PacketOutTeams packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getName(), out);
		out.writeByte(packet.getMode().getID());
		switch (packet.getMode()) {
		case CREATE_TEAM:
			writeString(packet.getDisplayName(), out);
			out.writeByte(packet.getFlags() & 0x03);
			writeString(optionToStringNameTagVisible(packet.getNameTagVisibility()), out);
			writeString(optionToStringCollision(packet.getCollisionRule()), out);
			writeVarInt(packet.getColor().getID(), out);
			writeString(packet.getPrefix(), out);
			writeString(packet.getSuffix(), out);
			if (packet.getEntities() == null) {
				writeVarInt(0, out);
				return;
			}
			writeVarInt(packet.getEntities().size(), out);
			for (String s : packet.getEntities()) {
				writeString(s, out);
			}
			break;
		case UPDATE_TEAM_INFO:
			writeString(packet.getDisplayName(), out);
			out.writeByte(packet.getFlags() & 0x03);
			writeString(optionToStringNameTagVisible(packet.getNameTagVisibility()), out);
			writeString(optionToStringCollision(packet.getCollisionRule()), out);
			writeVarInt(packet.getColor().getID(), out);
			writeString(packet.getPrefix(), out);
			writeString(packet.getSuffix(), out);
			break;
		case REMOVE_TEAM:
			break;
		case ADD_ENTITIES:
		case REMOVE_ENTITIES:
			writeVarInt(packet.getEntities().size(), out);
			for (String s : packet.getEntities()) {
				writeString(s, out);
			}
			break;
		}
	}

	private TeamOptionType getNameTagVisibility(String nameTagVisibility) {
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

	private TeamOptionType getCollisionRule(String collisionRule) {
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
	
	private String optionToStringNameTagVisible(TeamOptionType option) {
		switch (option) {
		case ALWAYS:
			return "always";
		case FOR_OTHER_TEAMS:
			return "hideForOwnTeam";
		case FOR_OWN_TEAM:
			return "hideForOtherTeam";
		case NEVER:
			return "never";
		}
		return null;
	}
	
	private String optionToStringCollision(TeamOptionType option) {
		switch (option) {
		case ALWAYS:
			return "always";
		case FOR_OTHER_TEAMS:
			return "pushOtherTeam";
		case FOR_OWN_TEAM:
			return "pushOwnTeam";
		case NEVER:
			return "never";
		}
		return null;
	}
	
	@Override
	public PacketOutTeams createPacketData() {
		return new PacketOutTeams();
	}
	
}
