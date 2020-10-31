package de.atlasmc.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class FloatTag extends AbstractTag {

	private float data;
	
	public FloatTag(String name, float data) {
		this.name = name;
		this.data = data;
	}

	public FloatTag() {}

	@Override
	public Float getData() {
		return data;
	}

	@Override
	public TagType getType() {
		return TagType.FLOAT;
	}
	
	void readD(DataInputStream input, boolean readName) throws IOException {
		data = input.readFloat();
	}
	
	void writeD(DataOutputStream output, boolean readName) throws IOException {
		output.writeFloat(data);
	}

}
