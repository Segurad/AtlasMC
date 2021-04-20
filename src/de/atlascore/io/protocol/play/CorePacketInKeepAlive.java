package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInKeepAlive;
import io.netty.buffer.ByteBuf;

public class CorePacketInKeepAlive extends AbstractPacket implements PacketInKeepAlive {

	public CorePacketInKeepAlive() {
		super(0x10, CoreProtocolAdapter.VERSION);
	}
	
	private long keepAliveID;

	@Override
	public void read(ByteBuf in) throws IOException {
		keepAliveID = in.readLong();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(keepAliveID);
	}

	@Override
	public long getKeepAliveID() {
		return keepAliveID;
	}

}
