package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.OutputStream;

public class NBTIOWriter extends AbstractNBTIOWriter {
	
	private OutputStream out;
	
	public NBTIOWriter(OutputStream out) {
		if (out == null) throw new IllegalArgumentException("DataOutput can not be null!");
		this.out = out;
	}
	
	@Override
	protected void ioWriteInt(int value) throws IOException {
		out.write((value >> 24) & 0xFF);
		out.write((value >> 16) & 0xFF);
		out.write((value >> 8) & 0xFF);
		out.write(value & 0xFF);
	}

	@Override
	protected void ioWriteByte(int value) throws IOException {
		out.write(value & 0xFF);
	}

	@Override
	protected void ioWriteBytes(byte[] buffer) throws IOException {
		out.write(buffer);
	}

	@Override
	protected void ioWriteShort(int value) throws IOException {
		out.write((value >> 8) & 0xFF);
		out.write(value & 0xFF);
	}

	@Override
	protected void ioWriteLong(long value) throws IOException {
		out.write((int) ((value >> 56) & 0xFF));
		out.write((int) ((value >> 48) & 0xFF));
		out.write((int) ((value >> 40) & 0xFF));
		out.write((int) ((value >> 32) & 0xFF));
		out.write((int) ((value >> 24) & 0xFF));
		out.write((int) ((value >> 16) & 0xFF));
		out.write((int) ((value >> 8) & 0xFF));
		out.write((int) (value & 0xFF));
	}

	@Override
	protected void ioWriteFloat(float value) throws IOException {
		ioWriteInt(Float.floatToIntBits(value));
	}

	@Override
	protected void ioWriteDouble(double value) throws IOException {
		ioWriteLong(Double.doubleToLongBits(value));
	}

	@Override
	public void close() {
		super.close();
		out = null;
	}

}