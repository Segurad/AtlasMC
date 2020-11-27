package de.atlascore.v1_16_3.io.pack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInSetDifficulty;

public class PacketInSetDifficultyV1_16_3 extends AbstractPacket implements PacketInSetDifficulty {

	public PacketInSetDifficultyV1_16_3() {
		super(0x02, V1_16_3.version);
	}

	private int dif;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		dif = input.readByte();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public int getDifficulty() {
		return dif;
	}

}
