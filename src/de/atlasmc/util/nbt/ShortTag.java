package de.atlasmc.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ShortTag extends AbstractTag {

	private short data;
	
	public ShortTag(String name, short data) {
		this.name = name;
		this.data = data;
	}

	public ShortTag() {}

	@Override
	public Short getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.SHORT;
	}
	
	void readD(DataInputStream input, boolean readName) throws IOException {
		data = input.readShort();
	}
	
	void writeD(DataOutputStream output, boolean readName) throws IOException {
		output.writeShort(data);
	}

}
