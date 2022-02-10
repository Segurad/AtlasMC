package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	void readD(DataInput input, boolean readName) throws IOException {
		data = input.readShort();
	}
	
	void writeD(DataOutput output, boolean readName) throws IOException {
		output.writeShort(data);
	}

	@Override
	public void setData(Object data) {
		this.data = (short) data;
	}
	
	@Override
	public ShortTag clone() {
		return (ShortTag) super.clone();
	}

}
