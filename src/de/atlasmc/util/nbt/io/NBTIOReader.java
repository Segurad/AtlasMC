package de.atlasmc.util.nbt.io;

import java.io.DataInput;
import java.io.IOException;
import de.atlasmc.util.ByteDataBuffer;

public class NBTIOReader extends AbstractNBTIOReader {
	
	private ByteDataBuffer buf;
	private boolean readBuf, writeBuf;
	private DataInput in;
	
	public NBTIOReader(DataInput in) {
		if (in == null) throw new IllegalArgumentException("DataInput can not be null!");
		this.in = new ByteDataBuffer();
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
		int val = in.readInt();
		if (writeBuf) buf.writeInt(val);
		return val;
	}

	@Override
	protected byte ioReadByte() throws IOException {
		if (readBuf())
			return buf.readByte();
		byte val = in.readByte();
		if (writeBuf) buf.writeByte(val);
		return val;
	}

	@Override
	protected void ioReadBytes(byte[] buffer) throws IOException {
		if (readBuf())
			buf.readFully(buffer);
		in.readFully(buffer);
		if (writeBuf) buf.write(buffer);
	}

	@Override
	protected short ioReadShort() throws IOException {
		if (readBuf())
			return buf.readShort();
		int val = in.readInt();
		if (writeBuf) buf.writeShort(val);
		return (short) val;
	}

	@Override
	protected long ioReadLong() throws IOException {
		if (readBuf())
			return buf.readLong();
		long val = in.readLong();
		if (writeBuf) buf.writeLong(val);
		return val;
	}

	@Override
	protected float ioReadFloat() throws IOException {
		if (readBuf())
			return buf.readFloat();
		float val = in.readFloat();
		if (writeBuf) buf.writeFloat(val);
		return val;
	}

	@Override
	protected double ioReadDouble() throws IOException {
		if (readBuf())
			return buf.readDouble();
		double val = in.readDouble();
		if (writeBuf) buf.writeDouble(val);
		return val;
	}
	
	private boolean readBuf() {
		if (readBuf) {
			if (buf.readableBytes() > 0) return true;
			readBuf = false;
		}
		return false;
	}

}
