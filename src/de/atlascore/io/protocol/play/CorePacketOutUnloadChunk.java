package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutUnloadChunk;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUnloadChunk extends AbstractPacket implements PacketOutUnloadChunk {

	private int x, z;
	
	public CorePacketOutUnloadChunk() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutUnloadChunk(int x, int z) {
		this();
		this.x = x;
		this.z = z;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readInt();
		z = in.readInt();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(x);
		out.writeInt(z);
	}

	@Override
	public int getChunkX() {
		return x;
	}

	@Override
	public int getChunkZ() {
		return z;
	}

}
