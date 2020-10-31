package de.atlasmc.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	void readD(DataInputStream input, boolean readName) throws IOException {
		int len = input.readInt();
		data = new byte[len];
		input.read(data);
	}

	@Override
	void writeD(DataOutputStream output, boolean readName) throws IOException {
		output.writeInt(data.length);
		output.write(data);
	}

}
