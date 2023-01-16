package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutStatistics;
import io.netty.buffer.ByteBuf;

public class CorePacketOutStatistics extends PacketIO<PacketOutStatistics> {

	@Override
	public void read(PacketOutStatistics packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		final int length = readVarInt(in)*3;
		int[] statistics = new int[length];
		for (int i = 0; i < length; i++) {
			statistics[i] = readVarInt(in);
		}
	}

	@Override
	public void write(PacketOutStatistics packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getStatistics().length/3, out);
		for (int i : packet.getStatistics()) {
			writeVarInt(i, out);
		}
	}
	
	@Override
	public PacketOutStatistics createPacketData() {
		return new PacketOutStatistics();
	}

}
