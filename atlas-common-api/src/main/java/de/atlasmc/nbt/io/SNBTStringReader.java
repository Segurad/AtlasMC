package de.atlasmc.nbt.io;

import java.util.Objects;

/**
 * Reads SNBT from a {@link CharSequence}
 * @see SNBTIOReader
 */
public class SNBTStringReader extends AbstractSNBTReader {

	private CharSequence in;
	private int pos;
	private int markPos = -1;

	public SNBTStringReader(CharSequence snbt) {
		this.in = Objects.requireNonNull(snbt, "snbt");
	}

	@Override
	protected int ioRead() {
		final CharSequence in = this.in;
		return pos < in.length() ? in.charAt(pos++) : -1;
	}

	@Override
	protected int ioPeek(int offset) {
		final CharSequence in = this.in;
		final int index = pos + offset;
		return index < in.length() ? in.charAt(index) : -1;
	}

	@Override
	protected void ioMark() {
		markPos = pos;
	}

	@Override
	protected void ioReset() {
		if (markPos == -1)
			throw new IllegalStateException("No mark set!");
		pos = markPos;
		markPos = -1;
	}

	@Override
	public void close() {
		super.close();
		in = null;
	}

}
