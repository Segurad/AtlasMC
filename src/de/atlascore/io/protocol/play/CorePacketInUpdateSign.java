package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInUpdateSign;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateSign extends PacketIO<PacketInUpdateSign> {

	@Override
	public void read(PacketInUpdateSign packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setLine1(readString(in));
		packet.setLine2(readString(in));
		packet.setLine3(readString(in));
		packet.setLine4(readString(in));
	}

	@Override
	public void write(PacketInUpdateSign packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeString(packet.getLine1(), out);
		writeString(packet.getLine2(), out);
		writeString(packet.getLine3(), out);
		writeString(packet.getLine4(), out);
	}

	@Override
	public PacketInUpdateSign createPacketData() {
		return new PacketInUpdateSign();
	}
	
}
