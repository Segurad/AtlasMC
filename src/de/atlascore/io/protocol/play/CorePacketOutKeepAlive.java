package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutKeepAlive;
import io.netty.buffer.ByteBuf;

public class CorePacketOutKeepAlive extends AbstractPacket implements PacketOutKeepAlive {

	private long keepAlive;
	
	public CorePacketOutKeepAlive() {
		super(0x1F, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutKeepAlive(long keepAlive) {
		this();
		this.keepAlive = keepAlive;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		keepAlive = in.readLong();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(keepAlive);
	}

	@Override
	public long getKeepAlive() {
		return keepAlive;
	}

}
