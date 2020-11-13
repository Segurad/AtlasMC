package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInUpdateStructureBlock;

public class PacketInUpdateStructureBlockV1_16_3 extends AbstractPacket implements PacketInUpdateStructureBlock {

	public PacketInUpdateStructureBlockV1_16_3() {
		super(0x2A, V1_16_3.version);
	}
	
	private SimpleLocation pos;
	private int action,mode,mirror,roation;
	private String name,metadata;
	private byte offx,offy,offz,sizex,sizey,sizez,flags;
	private float integrity;
	private long seed;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		pos = readPosition(input);
		action = readVarInt(input);
		mode = readVarInt(input);
		mirror = readVarInt(input);
		roation = readVarInt(input);
		name = readString(input);
		metadata = readString(input);
		offx = input.readByte();
		offy = input.readByte();
		offz = input.readByte();
		sizex = input.readByte();
		sizey = input.readByte();
		sizez = input.readByte();
		flags = input.readByte();
		integrity = input.readFloat();
		seed = readVarLong(input);
		
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public int Action() {
		return action;
	}

	@Override
	public int Mode() {
		return mode;
	}

	@Override
	public String Name() {
		return name;
	}

	@Override
	public byte OffsetX() {
		return offx;
	}

	@Override
	public byte OffsetY() {
		return offy;
	}

	@Override
	public byte OffsetZ() {
		return offz;
	}

	@Override
	public byte SizeX() {
		return sizex;
	}

	@Override
	public byte SizeY() {
		return sizey;
	}

	@Override
	public byte SizeZ() {
		return sizez;
	}

	@Override
	public int Mirror() {
		return mirror;
	}

	@Override
	public int Rotation() {
		return roation;
	}

	@Override
	public String Metadata() {
		return metadata;
	}

	@Override
	public float Integrity() {
		return integrity;
	}

	@Override
	public long Seed() {
		return seed;
	}

	@Override
	public byte Flags() {
		return flags;
	}

}
