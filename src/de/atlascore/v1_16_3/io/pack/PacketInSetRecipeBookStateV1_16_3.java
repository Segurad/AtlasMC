package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInSetRecipeBookState;

public class PacketInSetRecipeBookStateV1_16_3 extends AbstractPacket implements PacketInSetRecipeBookState {

	public PacketInSetRecipeBookStateV1_16_3() {
		super(0x1F, V1_16_3.version);
	}

	private int bookID;
	private boolean bookopen,filteractive;
	
	@Override
	public void read(int length, DataInputStream input) throws IOException {
		bookID = readVarInt(input);
		bookopen = input.readBoolean();
		filteractive = input.readBoolean();
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public int BookID() {
		return bookID;
	}

	@Override
	public boolean BookOpen() {
		return bookopen;
	}

	@Override
	public boolean FilterActive() {
		return filteractive;
	}

}
