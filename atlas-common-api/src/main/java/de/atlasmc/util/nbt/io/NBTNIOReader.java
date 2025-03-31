package de.atlasmc.util.nbt.io;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public class NBTNIOReader extends AbstractNBTIOReader {
	
	private ByteBuf in;
	
	public NBTNIOReader(ByteBuf in) {
		this(in, false);
	}
	
	/**
	 * Creates a new NBTNIOReader
	 * Unnamed root components are root compound tags without name and name length.
	 * Unnamed roots are only used in java protocol since version 764.
	 * @param in
	 * @param unnamedRoot
	 */
	public NBTNIOReader(ByteBuf in, boolean unnamedRoot) {
		super(unnamedRoot);
		if (in == null) 
			throw new IllegalArgumentException("ByteBuf can not be null!");
		this.in = in;
	}

	@Override
	protected void ioMark() {
		in.markReaderIndex();
	}

	@Override
	protected void ioReset() {
		in.resetReaderIndex();
	}

	@Override
	protected int ioReadInt() {
		return in.readInt();
	}

	@Override
	protected int ioReadByte() {
		if (in.readableBytes() > 0)
			return in.readByte();
		return -1;
	}

	@Override
	protected void ioReadBytes(byte[] buffer, int off, int length) {
		in.readBytes(buffer, off, length);
	}

	@Override
	protected short ioReadShort() {
		return in.readShort();
	}

	@Override
	protected long ioReadLong() {
		return in.readLong();
	}

	@Override
	protected float ioReadFloat() {
		return in.readFloat();
	}

	@Override
	protected double ioReadDouble() {
		return in.readDouble();
	}

	@Override
	public void close() {
		super.close();
		in = null;
	}

	@Override
	protected void skipBytes(int bytes) throws IOException {
		in.skipBytes(bytes);
	}

}
