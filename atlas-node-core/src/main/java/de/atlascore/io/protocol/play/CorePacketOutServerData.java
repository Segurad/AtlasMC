package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutServerData;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutServerData implements PacketIO<PacketOutServerData> {

	@Override
	public void read(PacketOutServerData packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.motd = readString(in, CHAT_MAX_LENGTH);
		if (in.readBoolean()) {
			int size = readVarInt(in);
			byte[] icon = packet.icon = new byte[size];
			in.readBytes(icon);
		}
		packet.enforceSecureChat = in.readBoolean();
	}

	@Override
	public void write(PacketOutServerData packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.motd, out);
		if (packet.icon != null) {
			out.writeBoolean(true);
			writeVarInt(packet.icon.length, out);
			out.writeBytes(packet.icon);
		}
		out.writeBoolean(packet.enforceSecureChat);
	}

	@Override
	public PacketOutServerData createPacketData() {
		return new PacketOutServerData();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutServerData.class);
	}

}
