package de.atlasmc.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ByteTag extends AbstractTag {

	private byte data;
	
	public ByteTag(String name, byte data) {
		this.name = name;
		this.data = data;
	}

	public ByteTag() {}

	@Override
	public Byte getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.BYTE;
	}
	
	void readD(DataInputStream input, boolean readName) throws IOException {
		data = input.readByte();
	}
	
	void writeD(DataOutputStream output, boolean readName) throws IOException {
		output.writeByte(data);
	}

}
