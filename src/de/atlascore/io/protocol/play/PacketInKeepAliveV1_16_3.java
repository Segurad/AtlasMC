package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInKeepAlive;
import io.netty.buffer.ByteBuf;

public class PacketInKeepAliveV1_16_3 extends AbstractPacket implements PacketInKeepAlive {

	public PacketInKeepAliveV1_16_3() {
		super(0x10, V1_16_3.version);
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
	public long KeepAliveID() {
		return keepAliveID;
	}

}
