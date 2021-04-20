package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateSign;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateSign extends AbstractPacket implements PacketInUpdateSign {

	public CorePacketInUpdateSign() {
		super(0x2B, CoreProtocolAdapter.VERSION);
	}
	
	private long pos;
	private String l1,l2,l3,l4;

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
		l1 = readString(in);
		l2 = readString(in);
		l3 = readString(in);
		l4 = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pos);
		writeString(l1, out);
		writeString(l2, out);
		writeString(l3, out);
		writeString(l4, out);
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public String getLine1() {
		return l1;
	}

	@Override
	public String getLine2() {
		return l2;
	}

	@Override
	public String getLine3() {
		return l3;
	}

	@Override
	public String getLine4() {
		return l4;
	}

}
