package de.atlascore.v1_16_3.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerDigging;

public class PacketInPlayerDiggingV1_16_3 extends AbstractPacket implements PacketInPlayerDigging {

	public PacketInPlayerDiggingV1_16_3() {
		super(0x1B, V1_16_3.version);
	}

	private int status;
	private SimpleLocation pos;
	private byte face;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		status = readVarInt(input);
		pos = readPosition(input);
		face = input.readByte();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int Status() {
		return status;
	}

	@Override
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public byte Face() {
		return face;
	}
	
	

}
