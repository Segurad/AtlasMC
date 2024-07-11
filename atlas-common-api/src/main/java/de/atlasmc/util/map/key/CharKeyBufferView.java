package de.atlasmc.util.map.key;

public class CharKeyBufferView extends CharKey {

	private final CharKeyBuffer buf;
	
	
	public CharKeyBufferView(CharKeyBuffer buf) {
		this.buf = buf;
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
	public String toString() {
		return buf.toString();
	}

}
