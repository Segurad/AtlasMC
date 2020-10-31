package de.atlasmc.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	void readD(DataInputStream input, boolean readName) throws IOException {
		data = input.readInt();
	}
	
	void writeD(DataOutputStream output, boolean readName) throws IOException {
		output.writeInt(data);
	}

}
