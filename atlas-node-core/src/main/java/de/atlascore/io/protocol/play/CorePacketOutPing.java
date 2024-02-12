package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutPing;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPing implements PacketIO<PacketOutPing>{

	@Override
	public void read(PacketOutPing packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.ping = in.readInt();
	}

	@Override
	public void write(PacketOutPing packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeInt(packet.ping);
	}

	@Override
	public PacketOutPing createPacketData() {
		return new PacketOutPing();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPing.class);
	}

}
