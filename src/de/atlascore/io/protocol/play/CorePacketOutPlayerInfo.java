package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Gamemode;
import de.atlasmc.chat.component.FinalComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutPlayerInfo;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerInfo extends AbstractPacket implements PacketOutPlayerInfo {

	private int action;
	private List<PlayerInfo> info;
	
	public CorePacketOutPlayerInfo() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutPlayerInfo(PlayerInfoAction action, List<PlayerInfo> info) {
		this();
		this.action = action.ordinal();
		this.info = info;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		action = readVarInt(in);
		final int count = readVarInt(in);
		info = new ArrayList<PlayerInfo>(count);
		switch (action) {
		case 0:
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
				info.add(new PlayerInfo(new UUID(most, least), name, textures, ping, new FinalComponent(displayName), gm));
			}
			break;
		case 1:
			for (int i = 0; i < count; i++) {
				long most = in.readLong();
				long least = in.readLong();
				Gamemode gm = Gamemode.getByID(readVarInt(in));
				info.add(new PlayerInfo(new UUID(most, least), gm));
			}
			break;
		case 2:
			for (int i = 0; i < count; i++) {
				long most = in.readLong();
				long least = in.readLong();
				int ping = readVarInt(in);
				info.add(new PlayerInfo(new UUID(most, least), ping));
			}
			break;
		case 3:
			for (int i = 0; i < count; i++) {
				long most = in.readLong();
				long least = in.readLong();
				boolean display = in.readBoolean();
				String displayName = null;
				if (display) 
					displayName = readString(in);
				info.add(new PlayerInfo(new UUID(most, least), new FinalComponent(displayName)));
			}
			break;
		case 4:
			for (int i = 0; i < count; i++) {
				long most = in.readLong();
				long least = in.readLong();
				info.add(new PlayerInfo(new UUID(most, least)));
			}
			break;
		default: break;
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(action, out);
		writeVarInt(info.size(), out);
		switch (action) {
		case 0:
			for (PlayerInfo i : info) {
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
		case 1:
			for (PlayerInfo i : info) {
				out.writeLong(i.getUUID().getMostSignificantBits());
				out.writeLong(i.getUUID().getLeastSignificantBits());
				writeVarInt(i.getGamemode().ordinal(), out);
			}
			break;
		case 2:
			for (PlayerInfo i : info) {
				out.writeLong(i.getUUID().getMostSignificantBits());
				out.writeLong(i.getUUID().getLeastSignificantBits());
				writeVarInt(i.getPing(), out);
				out.writeBoolean(i.hasDisplayName());
			}
			break;
		case 3:
			for (PlayerInfo i : info) {
				out.writeLong(i.getUUID().getMostSignificantBits());
				out.writeLong(i.getUUID().getLeastSignificantBits());
				out.writeBoolean(i.hasDisplayName());
				if (i.hasDisplayName())
					writeString(i.getDisplayName(), out);
			}
			break;
		case 4:
			for (PlayerInfo i : info) {
				out.writeLong(i.getUUID().getMostSignificantBits());
				out.writeLong(i.getUUID().getLeastSignificantBits());
			}
			break;
		default: break;
		}
	}

	@Override
	public PlayerInfoAction getAction() {
		return PlayerInfoAction.getByID(action);
	}

	@Override
	public List<PlayerInfo> getPlayers() {
		return info;
	}

}
