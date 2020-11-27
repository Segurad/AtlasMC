package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class IntTag extends AbstractTag {

	private int data;
	
	public IntTag(String name, int data) {
		this.name = name;
		this.data = data;
	}

	public IntTag() {}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.INT;
	}
	
	void readD(DataInput input, boolean readName) throws IOException {
		data = input.readInt();
	}
	
	void writeD(DataOutput output, boolean readName) throws IOException {
		output.writeInt(data);
	}

}
