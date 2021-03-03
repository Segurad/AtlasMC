package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateSign;

public class PacketInUpdateSignV1_16_3 extends AbstractPacket implements PacketInUpdateSign {

	public PacketInUpdateSignV1_16_3() {
		super(0x2B, V1_16_3.version);
	}
	
	private SimpleLocation pos;
	private String l1,l2,l3,l4;

	@Override
	public void read(int length, DataInput input) throws IOException {
		pos = readPosition(input);
		l1 = readString(input);
		l2 = readString(input);
		l3 = readString(input);
		l4 = readString(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

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
