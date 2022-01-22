package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.InputStream;

import de.atlasmc.util.ByteDataBuffer;

public class NBTIOReader extends AbstractNBTIOReader {
	
	private ByteDataBuffer buf;
	private boolean readBuf, writeBuf;
	private InputStream  in;
	
	public NBTIOReader(InputStream in) {
		if (in == null) throw new IllegalArgumentException("DataInput can not be null!");
		this.in = in;
		try {
			readNextEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void ioMark() {
		if (buf == null) buf = new ByteDataBuffer();
		else buf.clear();
		writeBuf = true;
	}

	@Override
	protected void ioReset() {
		readBuf = true;
		writeBuf = false;
	}

	@Override
	protected int ioReadInt() throws IOException {
		if (readBuf())
			return buf.readInt();
		int val = in.read() << 8 | in.read();
		val = val << 8 | in.read();
		val = val << 8 | in.read();
		if (writeBuf) buf.writeInt(val);
		return val;
	}

	@Override
	protected byte ioReadByte() throws IOException {
		if (readBuf())
			return buf.readByte();
		byte val = (byte) in.read();
		if (writeBuf) buf.writeByte(val);
		return val;
	}

	@Override
	protected void ioReadBytes(byte[] buffer) throws IOException {
		if (readBuf())
			buf.readFully(buffer);
		in.read(buffer);
		if (writeBuf) buf.write(buffer);
	}

	@Override
	protected short ioReadShort() throws IOException {
		if (readBuf())
			return buf.readShort();
		int val = in.read() << 8 | in.read();
		if (writeBuf) buf.writeShort(val);
		return (short) val;
	}

	@Override
	protected long ioReadLong() throws IOException {
		if (readBuf())
			return buf.readLong();
		long val = in.read() << 8 | in.read();
		val = val << 8 | in.read();
		val = val << 8 | in.read();
		val = val << 8 | in.read();
		val = val << 8 | in.read();
		val = val << 8 | in.read();
		val = val << 8 | in.read();
		if (writeBuf) buf.writeLong(val);
		return val;
	}

	@Override
	protected float ioReadFloat() throws IOException {
		return Float.intBitsToFloat(ioReadInt());
	}

	@Override
	protected double ioReadDouble() throws IOException {
		return Double.longBitsToDouble(ioReadLong());
	}
	
	private boolean readBuf() {
		if (readBuf) {
			if (buf.readableBytes() > 0) return true;
			readBuf = false;
		}
		return false;
	}

	@Override
	public void close() {
		super.close();
		in = null;
		buf = null;
	}

}
