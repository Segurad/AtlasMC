package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.SimpleLocation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInUpdateCommandBlock;

public class PacketInUpdateCommandBlockV1_16_3 extends AbstractPacket implements PacketInUpdateCommandBlock {

	public PacketInUpdateCommandBlockV1_16_3() {
		super(0x26, V1_16_3.version);
	}
	
	private SimpleLocation pos;
	private String cmd;
	private int mode;
	private byte flags;

	@Override
	public void read(int length, DataInputStream input) throws IOException {
		pos = readPosition(input);
		cmd = readString(input);
		mode = readVarInt(input);
		flags = input.readByte();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public SimpleLocation Position() {
		return pos;
	}

	@Override
	public String Command() {
		return cmd;
	}

	@Override
	public int Mode() {
		return mode;
	}

	@Override
	public byte Flags() {
		return flags;
	}

}
