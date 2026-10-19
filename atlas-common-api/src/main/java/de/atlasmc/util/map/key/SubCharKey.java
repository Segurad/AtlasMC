package de.atlasmc.util.map.key;

import java.util.Objects;

public class SubCharKey extends CharKey {

	private final CharKey parent;
	private final int start;
	private final int end;
	
	public SubCharKey(CharKey parent, int start, int end) {
		this.parent = parent;
		this.start = start;
		this.end = end;
	}

	@Override
	public int length() {
		return end-start;
	}

	@Override
	public char charAt(int index) {
		Objects.checkIndex(index, length());
		return parent.charAt(start+index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		Objects.checkFromToIndex(start, end, length());
		return new SubCharKey(parent, this.start + start, this.start + end);
	}

	@Override
	protected char[] getBuf() {
		return parent.getBuf();
	}
	
	@Override
	protected int getOffset() {
		return parent.getOffset() + start;
	}

}
