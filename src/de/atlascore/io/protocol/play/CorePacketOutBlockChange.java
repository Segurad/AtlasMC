package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutBlockChange;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockChange extends AbstractPacket implements PacketOutBlockChange {

	private long pos;
	private int blockState;
	
	public CorePacketOutBlockChange() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutBlockChange(long pos, int blockState) {
		this();
		this.pos = pos;
		this.blockState = blockState;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
		blockState = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pos);
		writeVarInt(blockState, out);
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public int getBlockStateID() {
		return blockState;
	}
	
	

}
