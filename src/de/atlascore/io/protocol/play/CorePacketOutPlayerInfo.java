package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.Gamemode;
import de.atlasmc.chat.ChatUtil;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutPlayerInfo;
import de.atlasmc.io.protocol.play.PacketOutPlayerInfo.PlayerInfo;
import de.atlasmc.io.protocol.play.PacketOutPlayerInfo.PlayerInfoAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerInfo extends PacketIO<PacketOutPlayerInfo> {

	@Override
	public void read(PacketOutPlayerInfo packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setAction(PlayerInfoAction.getByID(readVarInt(in)));
		final int count = readVarInt(in);
		List<PlayerInfo> infos = new ArrayList<>(count);
		switch (packet.getAction()) {
		case ADD_PLAYER:
			for (int i = 0; i < count; i++) {
				long most = in.readLong();
				long least = in.readLong();
				String name = readString(in);
				int properties = readVarInt(in);
				String textures = null;
				for (int j = 0; j < properties; j++) {
					String temp = readString(in);
					if (temp.equals("textures"))
						textures = readString(in);
					boolean signature = in.readBoolean();
					if (signature) readString(in);
				}
				Gamemode gm = Gamemode.getByID(readVarInt(in));
				int ping = readVarInt(in);
				boolean display = in.readBoolean();
				String displayName = null;
				if (display) 
					displayName = readString(in);
				infos.add(new PlayerInfo(new UUID(most, least), name, textures, ping, ChatUtil.toChat(displayName), gm));
			}
			break;
		case UPDATE_GAMEMODE:
			for (int i = 0; i < count; i++) {
				long most = in.readLong();
				long least = in.readLong();
				Gamemode gm = Gamemode.getByID(readVarInt(in));
				infos.add(new PlayerInfo(new UUID(most, least), gm));
			}
			break;
		case UPDATE_LATENCY:
			for (int i = 0; i < count; i++) {
				long most = in.readLong();
				long least = in.readLong();
				int ping = readVarInt(in);
				infos.add(new PlayerInfo(new UUID(most, least), ping));
			}
			break;
		case UPDATE_DISPLAY_NAME:
			for (int i = 0; i < count; i++) {
				long most = in.readLong();
				long least = in.readLong();
				boolean display = in.readBoolean();
				String displayName = null;
				if (display) 
					displayName = readString(in);
				infos.add(new PlayerInfo(new UUID(most, least), ChatUtil.toChat(displayName)));
			}
			break;
		case REMOVE_PLAYER:
			for (int i = 0; i < count; i++) {
				long most = in.readLong();
				long least = in.readLong();
				infos.add(new PlayerInfo(new UUID(most, least)));
			}
			break;
		default: break;
		}
	}

	@Override
	public void write(PacketOutPlayerInfo packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getAction().getID(), out);
		List<PlayerInfo> infos = packet.getPlayers();
		writeVarInt(infos.size(), out);
		switch (packet.getAction()) {
		case ADD_PLAYER:
			for (PlayerInfo i : infos) {
				out.writeLong(i.getUUID().getMostSignificantBits());
				out.writeLong(i.getUUID().getLeastSignificantBits());
				writeString(i.getName(), out);
				if (i.hasTextures()) {
					writeVarInt(1, out);
					writeString("textures", out);
					writeString(i.getTextures(), out);
					out.writeBoolean(false);
				} else writeVarInt(0, out);
				writeVarInt(i.getGamemode().ordinal(), out);
				writeVarInt(i.getPing(), out);
				out.writeBoolean(i.hasDisplayName());
				if (i.hasDisplayName())
					writeString(i.getDisplayName(), out);
			}
			break;
		case UPDATE_GAMEMODE:
			for (PlayerInfo i : infos) {
				out.writeLong(i.getUUID().getMostSignificantBits());
				out.writeLong(i.getUUID().getLeastSignificantBits());
				writeVarInt(i.getGamemode().ordinal(), out);
			}
			break;
		case UPDATE_LATENCY:
			for (PlayerInfo i : infos) {
				out.writeLong(i.getUUID().getMostSignificantBits());
				out.writeLong(i.getUUID().getLeastSignificantBits());
				writeVarInt(i.getPing(), out);
				out.writeBoolean(i.hasDisplayName());
			}
			break;
		case UPDATE_DISPLAY_NAME:
			for (PlayerInfo i : infos) {
				out.writeLong(i.getUUID().getMostSignificantBits());
				out.writeLong(i.getUUID().getLeastSignificantBits());
				out.writeBoolean(i.hasDisplayName());
				if (i.hasDisplayName())
					writeString(i.getDisplayName(), out);
			}
			break;
		case REMOVE_PLAYER:
			for (PlayerInfo i : infos) {
				out.writeLong(i.getUUID().getMostSignificantBits());
				out.writeLong(i.getUUID().getLeastSignificantBits());
			}
			break;
		default: break;
		}
	}

	@Override
	public PacketOutPlayerInfo createPacketData() {
		return new PacketOutPlayerInfo();
	}

}
