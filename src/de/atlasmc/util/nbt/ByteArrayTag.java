package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ByteArrayTag extends AbstractTag {

	private byte[] data;
	
	public ByteArrayTag(String name, byte[] data) {
		this.name = name;
		this.data = data;
	}
	
	public ByteArrayTag() {}

	@Override
	public byte[] getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.BYTE_ARRAY;
	}

	@Override
	void readD(DataInput input, boolean readName) throws IOException {
		int len = input.readInt();
		data = new byte[len];
		input.readFully(data);
	}

	@Override
	void writeD(DataOutput output, boolean readName) throws IOException {
		output.writeInt(data.length);
		output.write(data);
	}

}
