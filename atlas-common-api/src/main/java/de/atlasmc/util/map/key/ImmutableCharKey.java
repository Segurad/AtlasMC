package de.atlasmc.util.map.key;

import java.util.Arrays;

public final class ImmutableCharKey extends CharKey {
	
	private final char[] buf;
	
	public ImmutableCharKey(CharSequence sequence) {
		final int length = sequence.length();
		buf = new char[length];
		for (int i = 0; i < length; i++)
			buf[i] = sequence.charAt(i);
	}
	
	public ImmutableCharKey(char[] chars) {
		this(chars, 0, chars.length);
	}
	
	public ImmutableCharKey(char[] chars, int offset, int end) {
		buf = Arrays.copyOfRange(chars, offset, end);
	}

	@Override
	public int length() {
		return buf.length;
	}

	@Override
	public char charAt(int index) {
		return buf[index];
	}

	@Override
	public CharKey subSequence(int start, int end) {
		if (start < 0)
			throw new IndexOutOfBoundsException(start);
		if (end < 0 || end >= length())
			throw new IndexOutOfBoundsException(end);
		return new SubCharKey(this, start, end);
	}
	
	@Override
	public String toString() {
		return new String(buf);
	}

	@Override
	protected final char[] getBuf() {
		return buf;
	}

}
