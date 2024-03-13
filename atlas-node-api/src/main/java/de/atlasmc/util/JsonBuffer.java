package de.atlasmc.util;

import java.util.Arrays;

public class JsonBuffer implements CharSequence {
	
	private static final char 
	BEGIN_OBJECT = '{',
	END_OBJECT = '}',
	BEGIN_ARRAY = '[',
	END_ARRAY = ']';
	
	private char[] buff;
	private String stringcache;
	
	private int index;
	private boolean separator;
	
	public JsonBuffer beginObject(CharSequence key) {
		clearStringCache();
		appendKey(key);
		ensureSize(1);
		buff[index++] = BEGIN_OBJECT;
		separator = false;
		return this;
	}
	
	public JsonBuffer endObject() {
		clearStringCache();
		ensureSize(1);
		buff[index++] = END_OBJECT;
		return this;
	}
	
	public JsonBuffer beginArray(CharSequence key) {
		clearStringCache();
		appendKey(key);
		ensureSize(1);
		buff[index++] = BEGIN_ARRAY;
		separator = false;
		return this;
	}
	
	public JsonBuffer endArray() {
		clearStringCache();
		ensureSize(1);
		buff[index++] = END_ARRAY;
		return this;
	}
	
	public JsonBuffer appendText(CharSequence key, CharSequence text) {
		clearStringCache();
		appendKey(key);
		appendCharSequence(text);
		return this;
	}
	
	public JsonBuffer appendBoolean(CharSequence key, boolean value) {
		clearStringCache();
		appendKey(key);
		appendBoolean(value);
		return this;
	}
	
	public JsonBuffer appendDouble(CharSequence key, double value) {
		clearStringCache();
		appendKey(key);
		appendCharSequenceRaw(Double.toString(value));
		return this;
	}
	
	public JsonBuffer appendNull(CharSequence key) {
		clearStringCache();
		appendKey(key);
		appendNull();
		return this;
	}
	
	public JsonBuffer appendLong(CharSequence key, long value) {
		clearStringCache();
		appendKey(key);
		appendCharSequenceRaw(Long.toString(value));
		return this;
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
		int toEscape = countToEscape(sequence);
		ensureSize(2+sequence.length() + toEscape);
		buff[index++] = '"';
		final int length = sequence.length();
		for (int i = 0; i < length; i++) {
			char c = sequence.charAt(i);
			if (needsEscape(c))
				buff[index++] = '\\';
			buff[index++] = c;
		}
		buff[index++] = '"';
	}
	
	protected int countToEscape(CharSequence sequence) {
		final int length = sequence.length();
		int toEscape = 0;
		for (int i = 0; i < length; i++) {
			char c = sequence.charAt(i);
			if (needsEscape(c))
				toEscape++;
		}
		return toEscape;
	}
	
	protected boolean needsEscape(char c) {
		switch (c) {
		case '\\':
		case '"':
		case '\t':
		case '\n':
		case '\b':
		case '\r':
		case '\f':
			return true;
		default:
			return false;
		}
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

	public JsonBuffer append(CharSequence key, Object value) {
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
		return this;
	}
	
	public JsonBuffer appendRaw(CharSequence key, CharSequence value) {
		clearStringCache();
		appendKey(key);
		appendCharSequenceRaw(value);
		return this;
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
