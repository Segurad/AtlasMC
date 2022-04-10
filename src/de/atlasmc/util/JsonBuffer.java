package de.atlasmc.util;

import java.util.Arrays;

public class JsonBuffer implements CharSequence {
	
	private char[] buff;
	private String stringcache;
	
	private int index;
	private boolean separator;
	
	public void beginObject(CharSequence key) {
		clearStringCache();
		appendKey(key);
		ensureSize(1);
		buff[index++] = '{';
		separator = false;
	}
	
	public void endObject() {
		clearStringCache();
		ensureSize(1);
		buff[index++] = '}';
	}
	
	public void beginArray(CharSequence key) {
		clearStringCache();
		appendKey(key);
		ensureSize(1);
		buff[index++] = '[';
		separator = false;
	}
	
	public void endArray() {
		clearStringCache();
		ensureSize(1);
		buff[index++] = ']';
	}
	
	public void appendText(CharSequence key, CharSequence text) {
		clearStringCache();
		appendKey(key);
		appendCharSequence(text);
	}
	
	public void appendBoolean(CharSequence key, boolean value) {
		clearStringCache();
		appendKey(key);
		appendBoolean(value);
	}
	
	public void appendDouble(CharSequence key, double value) {
		clearStringCache();
		appendKey(key);
		appendCharSequenceRaw(Double.toString(value));
	}
	
	public void appendNull(CharSequence key) {
		clearStringCache();
		appendKey(key);
		appendNull();
	}
	
	public void appendLong(CharSequence key, long value) {
		clearStringCache();
		appendKey(key);
		appendCharSequenceRaw(Long.toString(value));
	}

	protected void appendKey(CharSequence key) {
		if (separator) {
			ensureSize(1);
			buff[index++] = ',';
		} else separator = true;
		if (key == null)
			return;
		ensureSize(3+key.length());
		appendCharSequence(key);
		buff[index++] = ':';
	}
	
	protected void appendNull() {
		ensureSize(4);
		buff[index++] = 'n';
		buff[index++] = 'u';
		buff[index++] = 'l';
		buff[index++] = 'l';
	}
	
	protected void appendBoolean(boolean value) {
		if (value) {
			ensureSize(4);
			buff[index++] = 't';
			buff[index++] = 'r';
			buff[index++] = 'u';
			buff[index++] = 'e';
		} else {
			ensureSize(5);
			buff[index++] = 'f';
			buff[index++] = 'a';
			buff[index++] = 'l';
			buff[index++] = 's';
			buff[index++] = 'e';
		}
	}
	
	/**
	 * Appends a CharSequence as value in JSON text format ("text")
	 * @param sequence
	 */
	protected void appendCharSequence(CharSequence sequence) {
		if (sequence == null) {
			appendNull();
			return;
		}
		ensureSize(2+sequence.length());
		buff[index++] = '"';
		for (int i = 0; i < sequence.length(); i++) {
			buff[index++] = sequence.charAt(i);
		}
		buff[index++] = '"';
	}
	
	/**
	 * Appends a CharSequence as raw value
	 * @param sequence
	 */
	protected void appendCharSequenceRaw(CharSequence sequence) {
		ensureSize(sequence.length());
		for (int i = 0; i < sequence.length(); i++) {
			buff[index++] = sequence.charAt(i);
		}
	}
	
	protected final void clearStringCache() {
		stringcache = null;
	}
	
	/**
	 * Checks if the buffer has the required size
	 * @param needed number of elements that should additionally fit in the buffer
	 */
	protected void ensureSize(int needed) {
		int newsize = index + needed;
		if (buff.length >= newsize)
			return;
		newsize *= 1.75;
		buff = Arrays.copyOf(buff, newsize);
	}

	@Override
	public String toString() {
		if (stringcache == null)
			stringcache = String.valueOf(buff, 0, index);
		return stringcache;
	}

	public void append(CharSequence key, Object value) {
		clearStringCache();
		appendKey(key);
		if (value == null)
			appendNull();
		else if (value instanceof Number)
			appendCharSequenceRaw(value.toString());
		else if (value instanceof Boolean)
			appendBoolean((boolean) value);
		else
			appendCharSequence(value.toString());
	}

	@Override
	public int length() {
		return index;
	}

	@Override
	public char charAt(int index) {
		return buff[index];
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return String.valueOf(buff, start, end);
	}
	
}
