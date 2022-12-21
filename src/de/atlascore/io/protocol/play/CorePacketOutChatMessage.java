package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.chat.ChatType;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutChatMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChatMessage extends CoreAbstractHandler<PacketOutChatMessage> {

	@Override
	public void read(PacketOutChatMessage packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setMessage(readString(in));
		packet.setType(ChatType.getByID(in.readByte()));
		long most = in.readLong();
		long least = in.readLong();
		packet.setSender(new UUID(most, least));
	}

	@Override
	public void write(PacketOutChatMessage packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getMessage(), out);
		out.writeByte(packet.getType().getID());
		UUID sender = packet.getSender();
		if (sender == null) {
			out.writeLong(0);
			out.writeLong(0);
		} else {
			out.writeLong(sender.getMostSignificantBits());
			out.writeLong(sender.getLeastSignificantBits());
		}
	}

	@Override
	public PacketOutChatMessage createPacketData() {
		return new PacketOutChatMessage();
	}

}
