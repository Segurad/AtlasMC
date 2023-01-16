package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutMultiBlockChange;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMultiBlockChange extends PacketIO<PacketOutMultiBlockChange> {
	
	@Override
	public void read(PacketOutMultiBlockChange packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setSection(in.readLong());
		final int size = readVarInt(in);
		long[] blocks = new long[size];
		for (int i = 0; i < size; i++) {
			blocks[i] = readVarLong(in);
		}
		packet.setBlocks(blocks);
	}

	@Override
	public void write(PacketOutMultiBlockChange packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getSection());
		writeVarInt(packet.getBlocks().length, out);
		for (long l : packet.getBlocks()) {
			writeVarLong(l, out);
		}
	}

	@Override
	public PacketOutMultiBlockChange createPacketData() {
		return new PacketOutMultiBlockChange();
	}

}
