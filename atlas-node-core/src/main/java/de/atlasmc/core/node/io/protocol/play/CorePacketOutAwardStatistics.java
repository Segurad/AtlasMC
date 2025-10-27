package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutAwardStatistics;
import io.netty.buffer.ByteBuf;

public class CorePacketOutAwardStatistics implements PacketIO<PacketOutAwardStatistics> {

	@Override
	public void read(PacketOutAwardStatistics packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		final int length = readVarInt(in)*3;
		int[] statistics = new int[length];
		for (int i = 0; i < length; i++) {
			statistics[i] = readVarInt(in);
		}
	}

	@Override
	public void write(PacketOutAwardStatistics packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.statistics.length/3, out);
		for (int i : packet.statistics) {
			writeVarInt(i, out);
		}
	}
	
	@Override
	public PacketOutAwardStatistics createPacketData() {
		return new PacketOutAwardStatistics();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutAwardStatistics.class);
	}

}
