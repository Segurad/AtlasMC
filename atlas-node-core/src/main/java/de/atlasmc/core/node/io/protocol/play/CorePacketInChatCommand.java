package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInChatCommand;
import io.netty.buffer.ByteBuf;

public class CorePacketInChatCommand implements PacketIO<PacketInChatCommand>{

	@Override
	public void read(PacketInChatCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.command = StringCodec.readString(in);
	}

	@Override
	public void write(PacketInChatCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
		StringCodec.writeString(packet.command, out);
	}

	@Override
	public PacketInChatCommand createPacketData() {
		return new PacketInChatCommand();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInChatCommand.class);
	}

}
