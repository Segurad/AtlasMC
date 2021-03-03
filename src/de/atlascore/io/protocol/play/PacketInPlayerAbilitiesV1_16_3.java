package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerAbilities;

public class PacketInPlayerAbilitiesV1_16_3 extends AbstractPacket implements PacketInPlayerAbilities {

	public PacketInPlayerAbilitiesV1_16_3() {
		super(0x1A, V1_16_3.version);
	}
	
	private byte flags;

	@Override
	public void read(int length, DataInput input) throws IOException {
		flags = input.readByte();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public byte Flags() {
		return flags;
	}

}
