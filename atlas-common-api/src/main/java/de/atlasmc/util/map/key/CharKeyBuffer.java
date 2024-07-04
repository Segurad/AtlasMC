package de.atlasmc.util.map.key;

import java.util.Arrays;

public class CharKeyBuffer extends CharKey {

	private char[] buf;
	private int length;
	private String text;
	private CharKeyBufferView view;
	
	public CharKeyBuffer() {
		this(32);
	}
	
	public CharKeyBuffer(int size) {
		buf = new char[size];
	}
	
	public CharKeyBuffer(CharKey key) {
		buf = Arrays.copyOf(key.getBuf(), key.length());
	}
	
	public CharKeyBuffer(char[] buf) {
		this(buf, 0, buf.length);
	}
	
	public CharKeyBuffer(char[] buf, int offset, int length) {
		buf = Arrays.copyOfRange(buf, offset, offset+length);
	}
	
	public CharKeyBuffer(CharSequence sequence) {
		final int length = sequence.length();
		buf = new char[length];
		append(sequence);
	}
	
	public void append(CharSequence sequence) {
		append(sequence, 0, sequence.length());
	}
	
	public void append(CharSequence sequence, final int offset, final int length) {
		if (length <= 0)
			return;
		ensureSize(length);
		for (int i = offset; i < length; i++)
			buf[this.length++] = sequence.charAt(i);
	}
	
	public void append(char[] buf) {
		append(buf, 0, buf.length);
	}
	
	public void append(char[] buf, int offset, int length) {
		if (length <= 0)
			return;
		ensureSize(length);
		final int end = offset+length;
		for (int i = offset; i < end; i++)
			this.buf[this.length++] = buf[i];
	}
	
	public void setLength(int length) {
		if (this.length == length)
			return;
		hash = 0;
		text = null;
		this.length = Math.clamp(length, 0, buf.length);
	}
	
	public void clear() {
		length = 0;
	}
	
	@Override
	public int length() {
		return length;
	}

	@Override
	public char charAt(int index) {
		if (index > length || index < 0)
			throw new ArrayIndexOutOfBoundsException(index);
		return buf[index];
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return new ImmutableCharKey(buf, 0, length);
	}

	@Override
	protected final char[] getBuf() {
		return buf;
	}
	
	/**
	 * Grows the buff when the current length + the needed length exceeds the buffs length
	 * @param length
	 */
	protected void ensureSize(int length) {
		if (length > 0)
			hash = 0;
		text = null;
		if (buf.length >= this.length + length)
			return;
		buf = Arrays.copyOf(buf, (int) ((buf.length + length) * 1.75));
	}
	
	@Override
	public String toString() {
		if (text == null)
			text = new String(buf, 0, length);
		return text;
	}
	
	public CharKeyBufferView getView() {
		if (view == null)
			view = new CharKeyBufferView(this);
		return view;
	}

	public void append(char c) {
		ensureSize(1);
		buf[length++] = c;
	}

}
