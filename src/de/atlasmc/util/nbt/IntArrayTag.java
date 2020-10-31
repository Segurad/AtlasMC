package de.atlasmc.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class IntArrayTag extends AbstractTag {

	private int[] data;
	
	public IntArrayTag(String name, int[] data) {
		this.data = data;
		this.name = name;
	}
	
	public IntArrayTag() {}

	@Override
	public int[] getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.INT_ARRAY;
	}

	@Override
	void readD(DataInputStream input, boolean readName) throws IOException {
		final int len = input.readInt();
		data = new int[len];
		for (int i = 0; i < len; i++) {
			data[i] = input.readInt();
		}
	}

	@Override
	void writeD(DataOutputStream output, boolean readName) throws IOException {
		output.writeInt(data.length);
		for (int i : data) {
			output.writeInt(i);
		}
	}

}
