package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.BossBar.BarColor;
import de.atlasmc.BossBar.BarStyle;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutBossBar;
import de.atlasmc.io.protocol.play.PacketOutBossBar.BossBarAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBossBar extends CoreAbstractHandler<PacketOutBossBar> {

	@Override
	public void read(PacketOutBossBar packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		long most = in.readLong();
		long least = in.readLong();
		packet.setUUID(new UUID(most, least));
		BossBarAction action = BossBarAction.getByID(readVarInt(in));
		switch (action) {
		case ADD: packet.setTitle(readString(in));
				packet.setHealth(in.readFloat());
				packet.setColor(BarColor.getByID(readVarInt(in)));
				packet.setStyle(BarStyle.getByID(readVarInt(in)));
				packet.setFlags(in.readUnsignedByte());
				break;
		case REMOVE: break;
		case UPDATE_HEALTH: packet.setHealth(in.readFloat());
				break;
		case UPDATE_TITLE: packet.setTitle(readString(in));
				break;
		case UPDATE_STYLE: packet.setColor(BarColor.getByID(readVarInt(in)));
				packet.setStyle(BarStyle.getByID(readVarInt(in)));
				break;
		case UPDATE_FLAGS: packet.setFlags(in.readUnsignedByte());
				break;
		}
	}

	@Override
	public void write(PacketOutBossBar packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		UUID uuid = packet.getUUID();
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeVarInt(packet.getAction().getID(), out);
		switch (packet.getAction()) {
		case ADD: writeString(packet.getTitle(), out);
				out.writeFloat(packet.getHealth());
				writeVarInt(packet.getColor().getID(), out);
				writeVarInt(packet.getStyle().getID(), out);
				out.writeByte(packet.getFlags());
				break;
		case REMOVE: break;
		case UPDATE_HEALTH: out.writeFloat(packet.getHealth());
				break;
		case UPDATE_TITLE: writeString(packet.getTitle(), out);
				break;
		case UPDATE_STYLE: writeVarInt(packet.getColor().getID(), out);
				writeVarInt(packet.getStyle().getID(), out);
				break;
		case UPDATE_FLAGS: out.writeByte(packet.getFlags());
				break;
		}
	}
	
	@Override
	public PacketOutBossBar createPacketData() {
		return new PacketOutBossBar();
	}

}
