package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

abstract class AbstractTag implements NBT {
	
	protected String name;
	
	@Override
	public void read(DataInput input, boolean readName) throws IOException {
		if (readName) {
			short len = input.readShort();
			byte[] buffer = new byte[len];
			input.readFully(buffer);
			name = new String(buffer);
		}
		readD(input, readName);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void write(DataOutput output, boolean readName) throws IOException {
		if (readName && name != null) {
			output.write(getType().getID());
			byte[] buffer = name.getBytes();
			output.writeShort(buffer.length);
			output.write(buffer);
		}
		writeD(output, readName);
	}

	abstract void readD(DataInput input, boolean readName) throws IOException;
	abstract void writeD(DataOutput output, boolean readName) throws IOException;
}
