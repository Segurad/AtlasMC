package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutUnloadChunk;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUnloadChunk extends AbstractPacket implements PacketOutUnloadChunk {

	private int x, y;
	
	public CorePacketOutUnloadChunk() {
		super(0x1C, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutUnloadChunk(int x, int y) {
		this();
		this.x = x;
		this.y = y;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readInt();
		y = in.readInt();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(x);
		out.writeInt(y);
	}

	@Override
	public int getChunkX() {
		return x;
	}

	@Override
	public int getChunkY() {
		return y;
	}

}
