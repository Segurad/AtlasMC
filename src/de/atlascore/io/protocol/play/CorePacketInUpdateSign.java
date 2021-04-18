package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateSign;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateSign extends AbstractPacket implements PacketInUpdateSign {

	public CorePacketInUpdateSign() {
		super(0x2B, CoreProtocolAdapter.VERSION);
	}
	
	private SimpleLocation pos;
	private String l1,l2,l3,l4;

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = readPosition(in);
		l1 = readString(in);
		l2 = readString(in);
		l3 = readString(in);
		l4 = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writePosition(pos, out);
		writeString(l1, out);
		writeString(l2, out);
		writeString(l3, out);
		writeString(l4, out);
	}

	@Override
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public String Line1() {
		return l1;
	}

	@Override
	public String Line2() {
		return l2;
	}

	@Override
	public String Line3() {
		return l3;
	}

	@Override
	public String Line4() {
		return l4;
	}

}
