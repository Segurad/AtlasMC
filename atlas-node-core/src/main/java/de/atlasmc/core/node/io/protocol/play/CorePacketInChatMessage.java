package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInChatMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketInChatMessage implements PacketIO<PacketInChatMessage> {

	@Override
	public void read(PacketInChatMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.message = readString(in, 256);
		packet.messageTimestamp = in.readLong();
		packet.salt = in.readLong();
		boolean hasSignature = in.readBoolean();
		if (hasSignature) {
			byte[] signature = new byte[256];
			in.readBytes(signature);
			packet.signature = signature;
		}
		packet.messageCount = readVarInt(in);
		packet.acknowledged = readBitSet(in, 20);
	}

	@Override
	public void write(PacketInChatMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.message, out);
		out.writeLong(packet.messageTimestamp);
		out.writeLong(packet.salt);
		byte[] signature = packet.signature;
		if (signature != null) {
			out.writeBoolean(true);
			out.writeBytes(signature);
		} else {
			out.writeBoolean(false);
		}
		writeVarInt(packet.messageCount, out);
		writeBitSet(packet.acknowledged, out);
	}
	
	@Override
	public PacketInChatMessage createPacketData() {
		return new PacketInChatMessage();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInChatMessage.class);
	}

}
