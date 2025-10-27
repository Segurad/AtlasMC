package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.BossBar.BarColor;
import de.atlasmc.node.BossBar.BarStyle;
import de.atlasmc.node.io.protocol.play.PacketOutBossBar;
import de.atlasmc.node.io.protocol.play.PacketOutBossBar.BossBarAction;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBossBar implements PacketIO<PacketOutBossBar> {

	@Override
	public void read(PacketOutBossBar packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		long most = in.readLong();
		long least = in.readLong();
		packet.uuid = new UUID(most, least);
		BossBarAction action = EnumUtil.getByID(BossBarAction.class, readVarInt(in));
		packet.action = action;
		switch (action) {
		case ADD: 
			packet.title = Chat.STREAM_CODEC.deserialize(in, handler.getCodecContext());
			packet.health = in.readFloat();
			packet.color = EnumUtil.getByID(BarColor.class, readVarInt(in));
			packet.style = EnumUtil.getByID(BarStyle.class, readVarInt(in));
			packet.flags = in.readUnsignedByte();
			break;
		case REMOVE: 
			break;
		case UPDATE_HEALTH: 
			packet.health = in.readFloat();
			break;
		case UPDATE_TITLE: 
			packet.title = Chat.STREAM_CODEC.deserialize(in, handler.getCodecContext());
			break;
		case UPDATE_STYLE: 
			packet.color = EnumUtil.getByID(BarColor.class, readVarInt(in));
			packet.style = EnumUtil.getByID(BarStyle.class, readVarInt(in));
			break;
		case UPDATE_FLAGS: 
			packet.flags = in.readUnsignedByte();
			break;
		default:
			throw new ProtocolException("Unknown action: " + action);
		}
	}

	@Override
	public void write(PacketOutBossBar packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		UUID uuid = packet.uuid;
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeVarInt(packet.action.getID(), out);
		switch (packet.action) {
		case ADD: 
			Chat.STREAM_CODEC.serialize(packet.title, out, handler.getCodecContext());
			out.writeFloat(packet.health);
			writeVarInt(packet.color.getID(), out);
			writeVarInt(packet.style.getID(), out);
			out.writeByte(packet.flags);
			break;
		case REMOVE: 
			break;
		case UPDATE_HEALTH: 
			out.writeFloat(packet.health);
			break;
		case UPDATE_TITLE: 
			Chat.STREAM_CODEC.serialize(packet.title, out, handler.getCodecContext());
			break;
		case UPDATE_STYLE: writeVarInt(packet.color.getID(), out);
			writeVarInt(packet.style.getID(), out);
			break;
		case UPDATE_FLAGS: out.writeByte(packet.flags);
			break;
		default:
			throw new ProtocolException("Unknown action: " + packet.action);
		}
	}
	
	@Override
	public PacketOutBossBar createPacketData() {
		return new PacketOutBossBar();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutBossBar.class);
	}

}
