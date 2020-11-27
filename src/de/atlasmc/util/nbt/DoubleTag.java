package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class DoubleTag extends AbstractTag {

	private double data;
	
	public DoubleTag(String name, double data) {
		this.name = name;
		this.data = data;
	}
	
	public DoubleTag() {}

	@Override
	public Double getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.DOUBLE;
	}

	@Override
	void readD(DataInput input, boolean readName) throws IOException {
		data = input.readDouble();
	}

	@Override
	void writeD(DataOutput output, boolean readName) throws IOException {
		output.writeDouble(data);
	}

}
