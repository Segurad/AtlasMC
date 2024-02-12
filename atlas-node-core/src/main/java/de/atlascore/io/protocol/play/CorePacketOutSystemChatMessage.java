package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSystemChatMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSystemChatMessage implements PacketIO<PacketOutSystemChatMessage> {

	@Override
	public void read(PacketOutSystemChatMessage packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setMessage(readString(in));
		packet.setActionbar(in.readBoolean());
	}

	@Override
	public void write(PacketOutSystemChatMessage packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getMessage(), out);
		out.writeBoolean(packet.isActionbar());
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
