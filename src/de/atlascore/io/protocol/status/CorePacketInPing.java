package de.atlascore.io.protocol.status;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.status.PacketInPing;
import io.netty.buffer.ByteBuf;

public class CorePacketInPing extends AbstractPacket implements PacketInPing {
	
	private long ping;
	
	public CorePacketInPing() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketInPing(long ping) {
		this();
		this.ping = ping;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		ping = in.readLong();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(ping);
	}

	@Override
	public long getPing() {
		return ping;
	}

}
