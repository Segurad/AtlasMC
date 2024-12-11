package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInUpdateSign;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateSign implements PacketIO<PacketInUpdateSign> {

	@Override
	public void read(PacketInUpdateSign packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setFront(in.readBoolean());
		packet.setLine1(readString(in, 384));
		packet.setLine2(readString(in, 384));
		packet.setLine3(readString(in, 384));
		packet.setLine4(readString(in, 384));
	}

	@Override
	public void write(PacketInUpdateSign packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		out.writeBoolean(packet.isFront());
		writeString(packet.getLine1(), out);
		writeString(packet.getLine2(), out);
		writeString(packet.getLine3(), out);
		writeString(packet.getLine4(), out);
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
