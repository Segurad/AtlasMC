package de.atlasmc.util.nbt.io;

import java.io.DataOutput;
import java.io.IOException;

public class NBTIOWriter extends AbstractNBTIOWriter {
	
	private final DataOutput out;
	
	public NBTIOWriter(DataOutput out) {
		if (out == null) throw new IllegalArgumentException("DataOutput can not be null!");
		this.out = out;
	}
	
	@Override
	protected void ioWriteInt(int value) throws IOException {
		out.writeInt(value);
	}

	@Override
	protected void ioWriteByte(int value) throws IOException {
		out.writeByte(value);
	}

	@Override
	protected void ioWriteBytes(byte[] buffer) throws IOException {
		out.write(buffer);
	}

	@Override
	protected void ioWriteShort(int value) throws IOException {
		out.writeShort(value);
	}

	@Override
	protected void ioWriteLong(long value) throws IOException {
		out.writeLong(value);
	}

	@Override
	protected void ioWriteFloat(float value) throws IOException {
		out.writeFloat(value);
	}

	@Override
	protected void ioWriteDouble(double value) throws IOException {
		out.writeDouble(value);
	}

}