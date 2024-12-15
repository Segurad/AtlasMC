package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInChatCommand;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketInChatCommand implements PacketIO<PacketInChatCommand>{

	@Override
	public void read(PacketInChatCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.command = readString(in, MAX_IDENTIFIER_LENGTH);
	}

	@Override
	public void write(PacketInChatCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.command, out);
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
