package de.atlasmc.util.map.key;

public class SubCharKey extends CharKey {

	private final CharKey parent;
	private final int start, end;
	
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
		if (index > length())
			throw new IndexOutOfBoundsException(index);
		return parent.charAt(start+index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		if (start < 0)
			throw new IndexOutOfBoundsException(start);
		if (end < 0 || end >= length())
			throw new IndexOutOfBoundsException(end);
		return new SubCharKey(parent, this.start + start, this.start + end);
	}

	@Override
	protected char[] getBuf() {
		return parent.getBuf();
	}

}
