package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutDisguisedChatMessage;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketOutDisguisedChatMessage implements PacketIO<PacketOutDisguisedChatMessage> {

	@Override
	public void read(PacketOutDisguisedChatMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.message = readTextComponent(in);
		packet.chatType = readVarInt(in);
		packet.sender = readTextComponent(in);
		if (in.readBoolean())
			packet.target = readTextComponent(in);
	}

	@Override
	public void write(PacketOutDisguisedChatMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeTextComponent(packet.message, out);
		writeVarInt(packet.chatType, out);
		writeTextComponent(packet.sender, out);
		Chat target = packet.target;
		out.writeBoolean(target != null);
		if (target != null)
			writeTextComponent(target, out);
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
