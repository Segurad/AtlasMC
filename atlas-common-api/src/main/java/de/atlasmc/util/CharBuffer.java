package de.atlasmc.util;

import java.util.Arrays;

public class CharBuffer implements CharSequence {
	
	private static final int DEFAULT_CAPACITY = 4096;
	
	private char[] buf;
	private int readerIndex;
	private int writerIndex;
	
	public CharBuffer() {
		this(DEFAULT_CAPACITY);
	}
	
	public CharBuffer(int capacity) {
		this.buf = new char[capacity];
	}
	
	public void append(char c) {
		ensureSize(1);
		buf[writerIndex++] = c;
	}
	
	public void append(CharSequence chars) {
		append(chars, 0, chars.length());
	}
	
	public void append(CharSequence chars, int offset, int length) {
		ensureSize(length);
		for (int i = offset; i < length; i++) {
			buf[writerIndex++] = chars.charAt(i);
		}
	}
	
	public void append(char[] chars) {
		append(chars, 0, chars.length);
	}
	
	public void append(char[] chars, int offset, int length) {
		ensureSize(length);
		for (int i = offset; i < length; i++) {
			buf[writerIndex++] = chars[i];
		}
	}
	
	public char get() {
		return buf[readerIndex];
	}
	
	public char get(int index) {
		if (index >= writerIndex)
			throw new IndexOutOfBoundsException(index);
		return buf[index];
	}
	
	public char read() {
		if (readableChars() > 0)
			return buf[readerIndex++];
		throw new IndexOutOfBoundsException("No more chars to read!");
	}
	
	public void get(int index, char[] dst) {
		get(index, dst, 0, dst.length);
	}
	
	public void get(int index, char[] dst, int offset, int length) {
		if (readerIndex + length > writerIndex)
			throw new IndexOutOfBoundsException();
		System.arraycopy(buf, index, dst, offset, length);
	}
	
	public void read(char[] dst) {
		read(dst, 0, dst.length);
	}
	
	public void read(char[] dst, int offset, int length) {
		if (readerIndex + length > writerIndex)
			throw new IndexOutOfBoundsException();
		System.arraycopy(buf, readerIndex, dst, offset, length);
		readerIndex += length;
	}
	
	public void ensureSize(int length) {
		int requiredSize = this.writerIndex + length;
		if (requiredSize <= buf.length)
			return;
		buf = Arrays.copyOf(buf, (int) (requiredSize * 1.75));
	}
	
	public int capacity() {
		return buf.length;
	}
	
	public void capacity(int capacity) {
		if (capacity > buf.length) {
			buf = Arrays.copyOf(buf, capacity);
		} else {
			setIndex(Math.min(readerIndex, capacity), capacity);
		}
	}
	
	public void clear() {
		setIndex(0, 0);
	}
	
	public void setIndex(int readerIndex, int writerIndex) {
		if (readerIndex > writerIndex)
			throw new IndexOutOfBoundsException("Reader index can not be higher than writer index!");
		if (writerIndex > buf.length)
			throw new IndexOutOfBoundsException(writerIndex);
		this.readerIndex = readerIndex;
		this.writerIndex = writerIndex;
	}
	
	public int getReaderIndex() {
		return readerIndex;
	}
	
	public int getWriterIndex() {
		return writerIndex;
	}
	
	public int readableChars() {
		return writerIndex - readerIndex;
	}
	
	public void setReaderIndex(int readerIndex) {
		if (readerIndex > writerIndex)
			throw new IndexOutOfBoundsException(readerIndex);
		this.readerIndex = readerIndex;
	}
	
	public void setWriterIndex(int writerIndex) {
		if (writerIndex > buf.length)
			throw new IndexOutOfBoundsException(writerIndex);
		if (writerIndex < readerIndex)
			throw new IndexOutOfBoundsException(writerIndex);
		this.writerIndex = writerIndex;
	}
	
	public void discardReadChars() {
		int readable = readableChars();
		System.arraycopy(buf, readerIndex, buf, 0, readable);
		setIndex(0, readable);
	}

	@Override
	public int length() {
		return writerIndex;
	}

	@Override
	public char charAt(int index) {
		return get(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return new String(buf, start, end - start);
	}

}
