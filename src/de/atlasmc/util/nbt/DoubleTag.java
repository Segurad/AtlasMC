package de.atlasmc.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	void readD(DataInputStream input, boolean readName) throws IOException {
		data = input.readDouble();
	}

	@Override
	void writeD(DataOutputStream output, boolean readName) throws IOException {
		output.writeDouble(data);
	}

}
