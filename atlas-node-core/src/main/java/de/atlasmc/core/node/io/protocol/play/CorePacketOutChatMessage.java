package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatSignature;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutChatMessage;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChatMessage implements PacketIO<PacketOutChatMessage> {

	@Override
	public void read(PacketOutChatMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.sender = readUUID(in);
		packet.index = readVarInt(in);
		if (in.readBoolean()) {
			byte[] signature = new byte[256];
			in.readBytes(signature);
			packet.signature = signature;
		}
		packet.message = readString(in, 256);
		packet.messageTimestamp = in.readLong();
		packet.salt = in.readLong();
		final int previousCount = readVarInt(in);
		if (previousCount > 0) {
			List<ChatSignature> previous = new ArrayList<>(previousCount);
			for (int i = 0; i < previousCount; i++) {
				int id = readVarInt(in);
				byte[] signature = null;
				if (id == 0) {
					signature = new byte[256];
					in.readBytes(signature);
				} else {
					id--;
				}
				previous.add(new ChatSignature(id, signature));
			}
		}
		final CodecContext context = con.getCodecContext();
		if (in.readBoolean()) {
			packet.unsignedContent = Chat.STREAM_CODEC.deserialize(in, context);
		}
		packet.filterType = readVarInt(in);
		if (packet.filterType == 2) {
			packet.filterBits = readBitSet(in);
		}
		packet.chatType = readVarInt(in);
		packet.senderName = Chat.STREAM_CODEC.deserialize(in, context);
		if (in.readBoolean())
			packet.targetName = Chat.STREAM_CODEC.deserialize(in, context);
	}

	@Override
	public void write(PacketOutChatMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeUUID(packet.sender, out);
		writeVarInt(packet.index, out);
		if (packet.signature != null) {
			out.writeBoolean(true);
			out.writeBytes(packet.signature);
		} else {
			out.writeBoolean(false);
		}
		writeString(packet.message, out);
		out.writeLong(packet.messageTimestamp);
		out.writeLong(packet.salt);
		List<ChatSignature> previous = packet.previous;
		if (previous == null || previous.isEmpty()) {
			writeVarInt(0, out);
		} else {
			writeVarInt(previous.size(), out);
			for (ChatSignature s : previous) {
				if (s.signature == null) {
					writeVarInt(s.id+1, out);
				} else {
					writeVarInt(0, out);
					out.writeBytes(packet.signature);
				}
			}
		}
		final CodecContext context = con.getCodecContext();
		if (packet.unsignedContent != null) {
			out.writeBoolean(true);
			Chat.STREAM_CODEC.serialize(packet.unsignedContent, out, context);
		} else {
			out.writeBoolean(false);
		}
		writeVarInt(packet.filterType, out);
		if (packet.filterType == 2) {
			writeBitSet(packet.filterBits, out);
		}
		writeVarInt(packet.chatType, out);
		Chat.STREAM_CODEC.serialize(packet.senderName, out, context);
		if (packet.targetName != null) {
			out.writeBoolean(true);
			Chat.STREAM_CODEC.serialize(packet.targetName, out, context);
		} else {
			out.writeBoolean(false);
		}
	}

	@Override
	public PacketOutChatMessage createPacketData() {
		return new PacketOutChatMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutChatMessage.class);
	}

}
