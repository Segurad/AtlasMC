package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInChatMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketInChatMessage extends CoreAbstractHandler<PacketInChatMessage> {

	@Override
	public void read(PacketInChatMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setMessage(readString(in, 256));
	}

	@Override
	public void write(PacketInChatMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.getMessage(), out);
	}
	
	@Override
	public PacketInChatMessage createPacketData() {
		return new PacketInChatMessage();
	}

}
