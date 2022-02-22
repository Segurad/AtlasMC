package de.atlasmc.util.nbt.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlasmc.util.nbt.TagType;

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

	@Override
	public void setData(Object data) {
		this.data = (long) data;
	}
	
	@Override
	public LongTag clone() {
		return (LongTag) super.clone();
	}

}
