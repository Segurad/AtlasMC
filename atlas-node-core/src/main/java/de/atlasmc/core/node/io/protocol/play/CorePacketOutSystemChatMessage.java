package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSystemChatMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSystemChatMessage implements PacketIO<PacketOutSystemChatMessage> {

	@Override
	public void read(PacketOutSystemChatMessage packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.message = Chat.STREAM_CODEC.deserialize(in, handler.getCodecContext());
		packet.actionbar = in.readBoolean();
	}

	@Override
	public void write(PacketOutSystemChatMessage packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		Chat.STREAM_CODEC.serialize(packet.message, out, handler.getCodecContext());
		out.writeBoolean(packet.actionbar);
	}

	@Override
	public PacketOutSystemChatMessage createPacketData() {
		return new PacketOutSystemChatMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSystemChatMessage.class);
	}

}
