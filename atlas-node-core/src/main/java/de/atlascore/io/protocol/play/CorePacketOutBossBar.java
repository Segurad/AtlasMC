package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.BossBar.BarColor;
import de.atlasmc.BossBar.BarStyle;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutBossBar;
import de.atlasmc.io.protocol.play.PacketOutBossBar.BossBarAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBossBar implements PacketIO<PacketOutBossBar> {

	@Override
	public void read(PacketOutBossBar packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		long most = in.readLong();
		long least = in.readLong();
		packet.uuid = new UUID(most, least);
		BossBarAction action = BossBarAction.getByID(readVarInt(in));
		packet.action = action;
		switch (action) {
		case ADD: 
			packet.title = readTextComponent(in);
			packet.health = in.readFloat();
			packet.color = BarColor.getByID(readVarInt(in));
			packet.style = BarStyle.getByID(readVarInt(in));
			packet.flags = in.readUnsignedByte();
			break;
		case REMOVE: 
			break;
		case UPDATE_HEALTH: 
			packet.health = in.readFloat();
			break;
		case UPDATE_TITLE: 
			packet.title = readTextComponent(in);
			break;
		case UPDATE_STYLE: 
			packet.color = BarColor.getByID(readVarInt(in));
			packet.style = BarStyle.getByID(readVarInt(in));
			break;
		case UPDATE_FLAGS: 
			packet.flags = in.readUnsignedByte();
			break;
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
			writeTextComponent(packet.title, out);
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
			writeTextComponent(packet.title, out);
			break;
		case UPDATE_STYLE: writeVarInt(packet.color.getID(), out);
			writeVarInt(packet.style.getID(), out);
			break;
		case UPDATE_FLAGS: out.writeByte(packet.flags);
			break;
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
