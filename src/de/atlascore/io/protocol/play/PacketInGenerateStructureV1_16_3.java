package de.atlascore.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInGenerateStructure;

public class PacketInGenerateStructureV1_16_3 extends AbstractPacket implements PacketInGenerateStructure {

	public PacketInGenerateStructureV1_16_3() {
		super(0x0F, V1_16_3.version);
	}

	private SimpleLocation simploc;
	private int levels;
	private boolean keepJigsaws;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		simploc = readPosition(input);
		levels = readVarInt(input);
		keepJigsaws = input.readBoolean();
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public SimpleLocation Position() {
		return simploc;
	}

	@Override
	public int Levels() {
		return levels;
	}

	@Override
	public boolean Keep_Jigsaws() {
		return keepJigsaws;
	}

}
