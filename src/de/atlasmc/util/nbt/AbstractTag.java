package de.atlasmc.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

abstract class AbstractTag implements NBT {
	
	protected String name;
	
	@Override
	public void read(DataInputStream input, boolean readName) throws IOException {
		if (readName) {
			short len = input.readShort();
			byte[] buffer = new byte[len];
			input.read(buffer);
			name = new String(buffer);
		}
		readD(input, readName);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void write(DataOutputStream output, boolean readName) throws IOException {
		if (readName && name != null) {
			output.write(getType().getID());
			byte[] buffer = name.getBytes();
			output.writeShort(buffer.length);
			output.write(buffer);
		}
		writeD(output, readName);
	}

	abstract void readD(DataInputStream input, boolean readName) throws IOException;
	abstract void writeD(DataOutputStream output, boolean readName) throws IOException;
}
