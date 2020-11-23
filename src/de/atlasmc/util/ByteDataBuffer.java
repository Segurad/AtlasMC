package de.atlasmc.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteDataBuffer implements DataOutput, DataInput {

	protected byte[] buffer;
	protected int count, pos;
	
	public ByteDataBuffer() {
		buffer = new byte[32];
	}
	
	public ByteDataBuffer(int size) {
		if (size < 0) throw new IllegalArgumentException("Negativ initial size: " + size);
		buffer = new byte[size];
	}
	
	
	@Override
	public boolean readBoolean() {
		if (pos > count) throw new ArrayIndexOutOfBoundsException("Bufferend reached!");
		byte value = buffer[pos++];
		return value == 1;
	}

	@Override
	public byte readByte()  {
		if (pos > count) throw new ArrayIndexOutOfBoundsException("Bufferend reached!");
		return buffer[pos++];
	}

	@Override
	public char readChar() {
		if (pos + 1 > count) throw new ArrayIndexOutOfBoundsException("Bufferend reached!");
		int value = buffer[pos++] << 8 + buffer[pos++];
		return (char) value;
	}

	@Override
	public double readDouble() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float readFloat() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int readInt() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readLine() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long readLong() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short readShort() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readUTF() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int readUnsignedByte() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readUnsignedShort() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int skipBytes(int n) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeByte(int v) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeBytes(String s) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeChar(int v) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeChars(String s) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDouble(double v) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeFloat(float v) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeInt(int v) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeLong(long v) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeShort(int v) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeUTF(String s) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
