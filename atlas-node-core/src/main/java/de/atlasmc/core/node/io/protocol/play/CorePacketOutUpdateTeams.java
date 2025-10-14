package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateTeams;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateTeams.Mode;
import de.atlasmc.node.scoreboard.TeamOptionType;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateTeams implements PacketIO<PacketOutUpdateTeams> {

	@Override
	public void read(PacketOutUpdateTeams packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.name = readString(in, MAX_IDENTIFIER_LENGTH);
		Mode mode = Mode.getByID(in.readByte());
		packet.mode = mode;
		switch (mode) {
		case CREATE_TEAM: {
				packet.displayName = readTextComponent(in);
				packet.flags = in.readByte();
				packet.nameTagVisibility = getNameTagVisibility(readString(in, 40));
				packet.collisionRule = getCollisionRule(readString(in, 40));
				packet.color = EnumUtil.getByID(ChatColor.class, readVarInt(in));
				packet.prefix = readTextComponent(in);
				packet.suffix = readTextComponent(in);
				final int size = readVarInt(in);
				if (size > 0) {
					List<String> entities = new ArrayList<>(size);
					for (int i = 0; i < size; i++) {
						entities.add(readString(in, MAX_IDENTIFIER_LENGTH));
					}
					packet.entities = (entities);
				} else {
					packet.entities = List.of();
				}
				break;
			}
		case REMOVE_TEAM:
			break;
		case UPDATE_TEAM_INFO:
			packet.displayName = readTextComponent(in);
			packet.flags = in.readByte();
			packet.nameTagVisibility = getNameTagVisibility(readString(in, 40));
			packet.collisionRule = getCollisionRule(readString(in, 40));
			packet.color = EnumUtil.getByID(ChatColor.class, readVarInt(in));
			packet.prefix = readTextComponent(in);
			packet.suffix = readTextComponent(in);
			break;
		case ADD_ENTITIES:
		case REMOVE_ENTITIES: {
				final int size = readVarInt(in);
				if (size > 0) {
					List<String> entities = new ArrayList<>(size);
					for (int i = 0; i < size; i++) {
						entities.add(readString(in, MAX_IDENTIFIER_LENGTH));
					}
					packet.entities = (entities);
				} else {
					packet.entities = List.of();
				}
				break;
			}
		default:
			throw new ProtocolException("Unknown mode: " + packet.mode);
		}
	}

	@Override
	public void write(PacketOutUpdateTeams packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.name, out);
		out.writeByte(packet.mode.getID());
		switch (packet.mode) {
		case CREATE_TEAM: {
				writeTextComponent(packet.displayName, out);
				out.writeByte(packet.flags & 0x03);
				writeString(optionToStringNameTagVisible(packet.nameTagVisibility), out);
				writeString(optionToStringCollision(packet.collisionRule), out);
				writeVarInt(packet.color.getID(), out);
				writeTextComponent(packet.prefix, out);
				writeTextComponent(packet.suffix, out);
				if (packet.entities == null) {
					writeVarInt(0, out);
					break;
				}
				final int size = packet.entities.size();
				writeVarInt(size, out);
				for (int i = 0; i < size; i++) {
					writeString(packet.entities.get(i), out);
				}
				break;
			}
		case UPDATE_TEAM_INFO:
			writeTextComponent(packet.displayName, out);
			out.writeByte(packet.flags & 0x03);
			writeString(optionToStringNameTagVisible(packet.nameTagVisibility), out);
			writeString(optionToStringCollision(packet.collisionRule), out);
			writeVarInt(packet.color.getID(), out);
			writeTextComponent(packet.prefix, out);
			writeTextComponent(packet.suffix, out);
			break;
		case REMOVE_TEAM:
			break;
		case ADD_ENTITIES:
		case REMOVE_ENTITIES: {
				if (packet.entities == null) {
					writeVarInt(0, out);
					break;
				}
				final int size = packet.entities.size();
				writeVarInt(size, out);
				for (int i = 0; i < size; i++) {
					writeString(packet.entities.get(i), out);
				}
				break;
			}
		default:
			throw new ProtocolException("Unknown mode: " + packet.mode);
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
			throw new IllegalArgumentException("Unknown option: " + nameTagVisibility);
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
			throw new IllegalArgumentException("Unknown option: " + collisionRule);
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
		default:
			throw new IllegalArgumentException("Unknwon option: " + option);
		}
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
		default:
			throw new IllegalArgumentException("Unknown option: " + option);
		}
	}
	
	@Override
	public PacketOutUpdateTeams createPacketData() {
		return new PacketOutUpdateTeams();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateTeams.class);
	}
	
}
