package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInUpdateSign;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateSign implements PacketIO<PacketInUpdateSign> {

	@Override
	public void read(PacketInUpdateSign packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.isFront = in.readBoolean();
		packet.line1 = readString(in, 384);
		packet.line2 = readString(in, 384);
		packet.line3 = readString(in, 384);
		packet.line4 = readString(in, 384);
	}

	@Override
	public void write(PacketInUpdateSign packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		out.writeBoolean(packet.isFront);
		writeString(packet.line1, out);
		writeString(packet.line2, out);
		writeString(packet.line3, out);
		writeString(packet.line4, out);
	}

	@Override
	public PacketInUpdateSign createPacketData() {
		return new PacketInUpdateSign();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInUpdateSign.class);
	}
	
}
