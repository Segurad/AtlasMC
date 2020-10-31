package de.atlasmc.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class LongTag extends AbstractTag {

	private long data;
	
	public LongTag(String name, long data) {
		this.data = data;
	}

	public LongTag() {}

	@Override
	public Long getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.LONG;
	}
	
	void readD(DataInputStream input, boolean readName) throws IOException {
		data = input.readLong();
	}
	
	void writeD(DataOutputStream output, boolean readName) throws IOException {
		output.writeLong(data);
	}

}
