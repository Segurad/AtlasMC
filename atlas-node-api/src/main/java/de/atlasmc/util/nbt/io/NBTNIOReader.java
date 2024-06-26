package de.atlasmc.util.nbt.io;

import java.io.IOException;
import io.netty.buffer.ByteBuf;

public class NBTNIOReader extends AbstractNBTIOReader {
	
	private ByteBuf in;
	
	public NBTNIOReader(ByteBuf in) {
		this(in, false);
	}
	
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
	protected byte ioReadByte() {
		return in.readByte();
	}

	@Override
	protected void ioReadBytes(byte[] buffer) {
		in.readBytes(buffer);
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
