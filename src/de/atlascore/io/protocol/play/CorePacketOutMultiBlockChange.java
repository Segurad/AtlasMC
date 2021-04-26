package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutMultiBlockChange;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMultiBlockChange extends AbstractPacket implements PacketOutMultiBlockChange {

	private long section;
	private long[] blocks;
	
	public CorePacketOutMultiBlockChange() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutMultiBlockChange(long section, long[] blocks) {
		this();
		this.section = section;
		this.blocks = blocks;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		section = in.readLong();
		final int size = readVarInt(in);
		blocks = new long[size];
		for (int i = 0; i < size; i++) {
			blocks[i] = readVarLong(in);
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(section);
		writeVarInt(blocks.length, out);
		for (long l : blocks) {
			writeVarLong(l, out);
		}
	}

	@Override
	public long getSection() {
		return section;
	}

	@Override
	public long[] getBlocks() {
		return blocks;
	}

}
