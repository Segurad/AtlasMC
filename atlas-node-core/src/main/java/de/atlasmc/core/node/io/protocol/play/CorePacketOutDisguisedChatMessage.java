package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutDisguisedChatMessage;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisguisedChatMessage implements PacketIO<PacketOutDisguisedChatMessage> {

	@Override
	public void read(PacketOutDisguisedChatMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final CodecContext context = con.getCodecContext();
		packet.message = Chat.STREAM_CODEC.deserialize(in, context);
		packet.chatType = readVarInt(in);
		packet.sender = Chat.STREAM_CODEC.deserialize(in, context);
		if (in.readBoolean())
			packet.target = Chat.STREAM_CODEC.deserialize(in, context);
	}

	@Override
	public void write(PacketOutDisguisedChatMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		final CodecContext context = con.getCodecContext();
		Chat.STREAM_CODEC.serialize(packet.message, out, context);
		writeVarInt(packet.chatType, out);
		Chat.STREAM_CODEC.serialize(packet.sender, out, context);
		Chat target = packet.target;
		out.writeBoolean(target != null);
		if (target != null)
			Chat.STREAM_CODEC.serialize(packet.target, out, context);
	}

	@Override
	public PacketOutDisguisedChatMessage createPacketData() {
		return new PacketOutDisguisedChatMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutDisguisedChatMessage.class);
	}

}
