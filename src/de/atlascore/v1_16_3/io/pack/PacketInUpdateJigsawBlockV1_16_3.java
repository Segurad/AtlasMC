package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInUpdateJigsawBlock;

public class PacketInUpdateJigsawBlockV1_16_3 extends AbstractPacket implements PacketInUpdateJigsawBlock {

	public PacketInUpdateJigsawBlockV1_16_3() {
		super(0x28, V1_16_3.version);
	}
	
	private SimpleLocation pos;
	private String name,target,pool,finalstate,jointtype;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		pos = readPosition(input);
		name = readString(input);
		target = readString(input);
		pool = readString(input);
		finalstate = readString(input);
		jointtype = readString(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public String Name() {
		return name;
	}

	@Override
	public String Target() {
		return target;
	}

	@Override
	public String Pool() {
		return pool;
	}

	@Override
	public String FinalState() {
		return finalstate;
	}

	@Override
	public String Jointtype() {
		return jointtype;
	}

}
