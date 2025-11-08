package de.atlasmc.nbt.io;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public class NBTNIOWriter extends AbstractNBTIOWriter {
	
	private ByteBuf out;
	
	public NBTNIOWriter(ByteBuf out) {
		this(out, false);
	}
	
	public NBTNIOWriter(ByteBuf out, boolean unnamedRoot) {
		super(unnamedRoot);
		if (out == null) 
			throw new IllegalArgumentException("ByteBuf can not be null!");
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
		out.writeBytes(buffer);
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

	@Override
	public void close() throws IOException {
		super.close();
		out = null;
	}

	@Override
	protected void ioWriteBytes(byte[] buffer, int offset, int length) throws IOException {
		out.writeBytes(buffer, offset, length);
	}
	
}