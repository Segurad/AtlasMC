package de.atlasmc.util.map.key;

import java.util.Objects;

public class CharKeyBufferView extends CharKey {

	private final CharKeyBuffer buf;
	
	public CharKeyBufferView(CharKeyBuffer buf) {
		this.buf = Objects.requireNonNull(buf, "buf");
	}
	
	@Override
	public int length() {
		return buf.length();
	}

	@Override
	public char charAt(int index) {
		return buf.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return buf.subSequence(start, end);
	}

	@Override
	protected final char[] getBuf() {
		return buf.getBuf();
	}
	
	@Override
	public int hashCode() {
		return buf.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return buf.equals(obj);
	}
	
	@Override
	public String toString() {
		return buf.toString();
	}

}
