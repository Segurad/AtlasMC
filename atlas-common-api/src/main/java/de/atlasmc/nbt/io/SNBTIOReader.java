package de.atlasmc.nbt.io;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Objects;

/**
 * Reads SNBT from a {@link Reader} using a internal buffer, wrapping the reader in a BufferedReader is not required.
 * For byte input use a {@link java.io.InputStreamReader} e.g. with {@link io.netty.buffer.ByteBufInputStream}.
 * @see SNBTReader
 */
public class SNBTIOReader extends AbstractSNBTReader {

	public static final int DEFAULT_BUFFER_SIZE = 8192;

	private Reader in;
	private char[] buf;
	private int pos;
	private int limit;
	private int markPos = -1;

	public SNBTIOReader(Reader in) {
		this(in, DEFAULT_BUFFER_SIZE);
	}

	public SNBTIOReader(Reader in, int bufferSize) {
		if (bufferSize <= 0)
			throw new IllegalArgumentException("Buffer size must be greater than 0: " + bufferSize);
		this.in = Objects.requireNonNull(in, "in");
		this.buf = new char[bufferSize];
	}

	@Override
	protected int ioRead() throws IOException {
		if (pos >= limit && !fill(1))
			return -1;
		return buf[pos++];
	}

	@Override
	protected int ioPeek(int offset) throws IOException {
		if (pos + offset >= limit && !fill(offset + 1))
			return -1;
		return buf[pos + offset];
	}

	/**
	 * Ensures that at least the given number of chars is available from the current position.
	 * Chars from the mark position on are retained.
	 * @param chars
	 * @return false if the end of the input was reached before
	 * @throws IOException
	 */
	private boolean fill(int chars) throws IOException {
		while (limit - pos < chars) {
			final int keep = markPos == -1 ? pos : markPos;
			if (keep > 0) { // discard consumed chars
				System.arraycopy(buf, keep, buf, 0, limit - keep);
				limit -= keep;
				pos -= keep;
				if (markPos != -1)
					markPos = 0;
			}
			if (limit == buf.length)
				buf = Arrays.copyOf(buf, buf.length << 1);
			final int read = in.read(buf, limit, buf.length - limit);
			if (read == -1)
				return false;
			limit += read;
		}
		return true;
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

	/**
	 * @implNote does not close the reader
	 */
	@Override
	public void close() {
		super.close();
		in = null;
		buf = null;
	}

}
