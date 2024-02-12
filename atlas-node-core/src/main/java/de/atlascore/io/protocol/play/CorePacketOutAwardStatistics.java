package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutAwardStatistics;
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
		writeVarInt(packet.getStatistics().length/3, out);
		for (int i : packet.getStatistics()) {
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
