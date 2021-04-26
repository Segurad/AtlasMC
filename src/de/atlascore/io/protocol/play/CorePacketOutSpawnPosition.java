package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSpawnPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnPosition extends AbstractPacket implements PacketOutSpawnPosition {

	private long pos;
	
	public CorePacketOutSpawnPosition() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSpawnPosition(long position) {
		this();
		this.pos = position;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pos);
	}

	@Override
	public long getPosition() {
		return pos;
	}

}
