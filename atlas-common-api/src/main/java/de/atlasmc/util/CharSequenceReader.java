package de.atlasmc.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.InvalidMarkException;

/**
 * Reader implementation for {@link CharSequence}s
 */
public class CharSequenceReader extends Reader {

	private CharSequence source;
	
	private int readerIndex = 0;
	private int mark = -1;
	
	public CharSequenceReader(CharSequence sequence) {
		if (sequence == null)
			throw new IllegalArgumentException("Sequence can not be null!");
		this.source = sequence;
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		ensureOpen();
		if (cbuf == null)
			throw new IllegalArgumentException("Cbuf can not be null!");
		if (off < 0)
			throw new IllegalArgumentException("Offset can not be lower than 0: " + off);
		int transfered = 0;
		for (int i = off; i < cbuf.length && readerIndex < source.length(); readerIndex++, transfered++, i++) {
			cbuf[i] = source.charAt(readerIndex);
		}
		return transfered;
	}
	
	@Override
	public int read(CharBuffer target) throws IOException {
		ensureOpen();
		if (target == null)
			throw new IllegalArgumentException("Target can not be null!");
		int transfered = 0;
		for (; readerIndex < source.length(); readerIndex++, transfered++) {
			target.append(source.charAt(readerIndex));
		}
		return transfered;
	}

	@Override
	public void close() throws IOException {
		source = null;
	}
	
	@Override
	public int read() throws IOException {
		ensureOpen();
		if (readerIndex >= source.length())
			throw new EOFException();
		return source.charAt(readerIndex++);
	}
	
	@Override
	public long skip(long n) throws IOException {
		ensureOpen();
		int skipped = 0;
		if (n+readerIndex > source.length()) {
			skipped = source.length() - readerIndex;
			readerIndex = source.length();
		} else {
			skipped = (int) n;
			readerIndex += n;
		}
		return skipped;
	}
	
	@Override
	public long transferTo(Writer out) throws IOException {
		ensureOpen();
		if (out == null)
			throw new IllegalArgumentException("Out can not be null!");
		int transfered = 0;
		for (; readerIndex < source.length(); readerIndex++, transfered++) {
			out.write(source.charAt(readerIndex));
		}
		return transfered;
	}
	
	protected void ensureOpen() {
		if (source == null)
			throw new IllegalStateException("Stream closed!");
	}
	
	/**
	 * @param readAheadLimit
	 * @implNote readAheadLimit is ignored in this implementation
	 */
	@Override
	public void mark(int readAheadLimit) throws IOException {
		ensureOpen();
		this.mark = readerIndex;
	}
	
	@Override
	public boolean markSupported() {
		return true;
	}
	
	@Override
	public void reset() throws IOException {
		if (mark == -1)
			throw new InvalidMarkException();
		readerIndex = mark;
		mark = -1;
	}
	
	@Override
	public boolean ready() throws IOException {
		ensureOpen();
		return true;
	}

}
