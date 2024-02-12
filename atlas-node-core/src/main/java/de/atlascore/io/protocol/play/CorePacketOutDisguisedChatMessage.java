package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutDisguisedChatMessage;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutDisguisedChatMessage implements PacketIO<PacketOutDisguisedChatMessage> {

	@Override
	public void read(PacketOutDisguisedChatMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.message = readString(in);
		packet.chatType = readVarInt(in);
		packet.source = readString(in);
		if (in.readBoolean())
			packet.target = readString(in);
	}

	@Override
	public void write(PacketOutDisguisedChatMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.message, out);
		writeVarInt(packet.chatType, out);
		writeString(packet.source, out);
		String target = packet.target;
		out.writeBoolean(target != null);
		if (target != null)
			writeString(target, out);
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
