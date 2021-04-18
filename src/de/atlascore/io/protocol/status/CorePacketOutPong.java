package de.atlascore.io.protocol.status;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.status.PacketOutPong;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPong extends AbstractPacket implements PacketOutPong {

	private long pong;
	
	public CorePacketOutPong() {
		super(0x01, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutPong(long pong) {
		this();
		this.pong = pong;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		pong = in.readLong();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pong);
	}

}
