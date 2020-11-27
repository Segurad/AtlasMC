package de.atlasmc.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Arrays;

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
	
	public ByteDataBuffer(byte[] data) {
		this.buffer = data;
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
	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	@Override
	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public void readFully(byte[] buffer) {
		readFully(buffer, 0, buffer.length);
	}

	@Override
	public void readFully(byte[] buffer, int off, int length) {
		for (int i = off; i < length; i++) {
			if (pos > count) throw new ArrayIndexOutOfBoundsException("Bufferend reached!");
			buffer[i] = this.buffer[pos++];
		}
	}

	@Override
	public int readInt() {
		if (pos + 3 > count) throw new ArrayIndexOutOfBoundsException("Bufferend reached!");
		int value = buffer[pos++] << 8 +
				buffer[pos++] << 8 +
				buffer[pos++] << 8 + 
				buffer[pos++];
		return value;
	}

	@Override
	public String readLine() {
		return null;
	}

	@Override
	public long readLong() {
		if (pos + 7 > count) throw new ArrayIndexOutOfBoundsException("Bufferend reached!");
		long value = buffer[pos++] << 8 +
				buffer[pos++] << 8 +
				buffer[pos++] << 8 + 
				buffer[pos++] << 8 +
				buffer[pos++] << 8 +
				buffer[pos++] << 8 +
				buffer[pos++] << 8 +
				buffer[pos++];
		return value;
	}

	@Override
	public short readShort() {
		if (pos + 1 > count) throw new ArrayIndexOutOfBoundsException("Bufferend reached!");
		int value = buffer[pos++] << 8 + buffer[pos++];
		return (short) value;
	}

	@Override
	public String readUTF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int readUnsignedByte() {
		return readByte() & 0xFF;
	}

	@Override
	public int readUnsignedShort() {
		return 0;
	}

	@Override
	public int skipBytes(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void write(int value) {
		writeByte(value);
	}

	@Override
	public void write(byte[] buffer) {
		write(buffer, 0, buffer.length);
	}

	@Override
	public void write(byte[] buffer, int off, int length) {
		checkCapacity(count + length);
		for (int i = off; i < length; i++) {
			this.buffer[count++] = buffer[i];
		}
	}

	@Override
	public void writeBoolean(boolean value) {
		checkCapacity(count+1);
		buffer[count++] = (byte) (value ? 1 : 0);
	}

	@Override
	public void writeByte(int value) {
		checkCapacity(count+1);
		buffer[count++] = (byte) (value);
	}

	@Override
	public void writeBytes(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeChar(int v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeChars(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDouble(double value) {
		writeLong(Double.doubleToLongBits(value));
	}

	@Override
	public void writeFloat(float value) {
		writeInt(Float.floatToIntBits(value));
	}

	@Override
	public void writeInt(int value) {
		checkCapacity(count+4);
		buffer[count++] = (byte) (value >> 24);
		buffer[count++] = (byte) (value >> 16);
		buffer[count++] = (byte) (value >> 8);
		buffer[count++] = (byte) value;
	}

	@Override
	public void writeLong(long value) {
		checkCapacity(count+8);
		buffer[count++] = (byte) (value >> 56);
		buffer[count++] = (byte) (value >> 48);
		buffer[count++] = (byte) (value >> 40);
		buffer[count++] = (byte) (value >> 32);
		buffer[count++] = (byte) (value >> 24);
		buffer[count++] = (byte) (value >> 16);
		buffer[count++] = (byte) (value >> 8);
		buffer[count++] = (byte) value;
	}

	@Override
	public void writeShort(int value) {
		checkCapacity(count+2);
		buffer[count++] = (byte) (value >> 8);
		buffer[count++] = (byte) value;
	}

	@Override
	public void writeUTF(String s) {
		// TODO Auto-generated method stub
		
	}

	public byte[] toByteArray() {
		return Arrays.copyOf(buffer, count);
	}

	private void checkCapacity(int minCapacity) {
		if (minCapacity - buffer.length > 0) grow(minCapacity);
	}

	private void grow(int minCapacity) {
		int oldCapacity = buffer.length;
		int newCapacity = oldCapacity << 1;
		if (newCapacity - minCapacity < 0) newCapacity = minCapacity;
		if (newCapacity < 0) {
			if (minCapacity < 0) throw new OutOfMemoryError();
			newCapacity = Integer.MAX_VALUE;
		}

		buffer = Arrays.copyOf(buffer, newCapacity);
	}
}
