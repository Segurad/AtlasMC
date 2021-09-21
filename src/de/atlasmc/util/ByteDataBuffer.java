package de.atlasmc.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;
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
	public boolean readBoolean() throws IOException {
		if (pos > count) throw new EOFException("Bufferend reached!");
		byte value = buffer[pos++];
		return value == 1;
	}

	@Override
	public byte readByte() throws IOException {
		if (pos > count) throw new EOFException("Bufferend reached!");
		return buffer[pos++];
	}

	@Override
	public char readChar() throws IOException {
		if (pos + 1 > count) throw new EOFException("Bufferend reached!");
		int value = buffer[pos++] << 8 + buffer[pos++];
		return (char) value;
	}

	@Override
	public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	@Override
	public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public void readFully(byte[] buffer) throws IOException {
		readFully(buffer, 0, buffer.length);
	}

	@Override
	public void readFully(byte[] buffer, int off, int length) throws IOException {
		for (int i = off; i < length; i++) {
			if (pos > count) throw new EOFException("Bufferend reached!");
			buffer[i] = this.buffer[pos++];
		}
	}

	@Override
	public int readInt() throws IOException {
		if (pos + 3 > count) throw new EOFException("Bufferend reached!");
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
	public long readLong() throws IOException {
		if (pos + 7 > count) throw new EOFException("Bufferend reached!");
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
	public short readShort() throws IOException {
		if (pos + 1 > count) throw new EOFException("Bufferend reached!");
		int value = buffer[pos++] << 8 + buffer[pos++];
		return (short) value;
	}

	@Override
	public String readUTF() throws IOException {
		final int length = readUnsignedShort();
		char[] buf = new char[1024];
		int index = 0;
		String s = "";
		for (int i = 0; i < length; i++) {
			int first = readByte();
			if (index >= 1024) {
				s += buf;
				index = 0;
			}
			if ((first & 0x40) == 0) {
				buf[index++] = (char) (first & 0x7F);
			} else if ((first & 0xE0) == 0xC0) {
				int b = readByte();
				if ((b & 0xC0) != 0x80) throw new UTFDataFormatException();
				buf[index++] = (char) (((first & 0x1F) << 6) | (b & 0x3F));
			} else if ((first & 0xF0) == 0xE0) {
				int b = readByte();
				if ((b & 0xC0) != 0x80) throw new UTFDataFormatException();
				int c = readByte();
				if ((c & 0xC0) != 0x80) throw new UTFDataFormatException();
				buf[index++] = (char) (((first & 0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F));
			}
		}
		if (index > 0) s += new String(buf, 0, index); 
		return s;
	}

	@Override
	public int readUnsignedByte() throws IOException {
		return readByte() & 0xFF;
	}

	@Override
	public int readUnsignedShort() throws IOException {
		return readUnsignedByte() | readUnsignedByte();
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
		final int length = s.length();
		if (length == 0) return;
		for (int i = 0; i < length; i++) {
			writeByte(s.charAt(i));
		}
	}

	@Override
	public void writeChar(int v) {
		writeByte(0xFF & (v >> 8));
		writeByte(0xFF & v);
	}

	@Override
	public void writeChars(String s) {
		final int length = s.length();
		if (length == 0) return;
		for (int i = 0; i < length; i++) {
			writeChar(s.charAt(i));
		}
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
	public void writeUTF(String s) throws UTFDataFormatException {
		final int length = s.length();
		if (length == 0) return;
		int bytes = 0;
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			if (c >= 1 && c <= 0x7F) {
				bytes++;
			} else if (c == 0 || (c >= 0x80 && c <= 0x7FF)) {
				bytes+=2;
			} else if (c >= 0x800 && c <= 0xFFFF) {
				bytes+=3;
			}
		}
		if (bytes > 65535) throw new UTFDataFormatException();
		writeShort(bytes);
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			if (c >= 1 && c <= 0x7F) {
				write(c);
			} else if (c == 0 || (c >= 0x80 && c <= 0x7FF)) {
				write(0xc0 | (0x1f & (c >> 6)));
				write(0x80 | (0x3f & c));
			} else if (c >= 0x800 && c <= 0xFFFF) {
				write(0xe0 | (0x0f & (c >> 12)));
				write(0x80 | (0x3f & (c >>  6)));
				write(0x80 | (0x3f & c));
			}
		}
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

	public int readableBytes() {
		return count-pos;
	}

	public void clear() {
		count = 0;
		pos = 0;
	}
}
