package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutDeleteMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDeleteMessage implements PacketIO<PacketOutDeleteMessage> {

	@Override
	public void read(PacketOutDeleteMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		if ((packet.messageID = readVarInt(in)) == 0) {
			byte[] signature = new byte[256];
			in.readBytes(signature);
			packet.signature = signature;
		}
	}

	@Override
	public void write(PacketOutDeleteMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.messageID, out);
		if (packet.messageID == 0) {
			out.writeBytes(packet.signature);
		}
	}

	@Override
	public PacketOutDeleteMessage createPacketData() {
		return new PacketOutDeleteMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutDeleteMessage.class);
	}

}
