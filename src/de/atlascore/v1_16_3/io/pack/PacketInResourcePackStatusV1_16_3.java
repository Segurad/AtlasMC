package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInResourcePackStatus;

public class PacketInResourcePackStatusV1_16_3 extends AbstractPacket implements PacketInResourcePackStatus {

	public PacketInResourcePackStatusV1_16_3() {
		super(0x21, V1_16_3.version);
	}

	public int result;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		result = readVarInt(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int Result() {
		return result;
	}

}
