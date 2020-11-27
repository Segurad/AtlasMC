package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	void readD(DataInput input, boolean readName) throws IOException {
		data = input.readLong();
	}
	
	void writeD(DataOutput output, boolean readName) throws IOException {
		output.writeLong(data);
	}

}
