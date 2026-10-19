package de.atlasmc.command.argparser;

import de.atlasmc.command.CommandStringReader;
import de.atlasmc.command.CommandSyntaxException;

/**
 * Reads a fixed number of space separated coordinates.
 * Each coordinate may be prefixed with {@code ~} (relative) or {@code ^} (local).
 * The number after a prefix is optional and defaults to 0.
 * Local coordinates can not be mixed with world (absolute or relative) coordinates.
 */
final class CoordinateReader {

	private final CommandStringReader reader;
	private final int start;
	private final int count;
	private final String type;
	private int index;
	private boolean local;
	private boolean relative;

	/**
	 * @param reader to read from
	 * @param count number of coordinates to read
	 * @param type name of the coordinate type used in error messages e.g. int or double
	 */
	CoordinateReader(CommandStringReader reader, int count, String type) {
		this.reader = reader;
		this.start = reader.getCursor();
		this.count = count;
		this.type = type;
	}

	/**
	 * Whether or not the coordinates are local (prefixed with {@code ^})
	 * @return true if local
	 */
	boolean isLocal() {
		return local;
	}

	/**
	 * Whether or not the last read coordinate was relative (prefixed with {@code ~})
	 * @return true if relative
	 */
	boolean isRelative() {
		return relative;
	}

	int readInt() {
		readPrefix();
		final int value = hasValue() || !hasPrefix() ? reader.readInt() : 0;
		finish();
		return value;
	}

	double readDouble() {
		readPrefix();
		final double value = hasValue() || !hasPrefix() ? reader.readDouble() : 0;
		finish();
		return value;
	}

	private boolean hasPrefix() {
		return local || relative;
	}

	private void readPrefix() {
		final char prefix = reader.canRead() ? reader.peek() : 0;
		final boolean isLocal = prefix == '^';
		if (index == 0) {
			local = isLocal;
		} else if (local != isLocal) {
			throw new CommandSyntaxException(reader.getCommand(), reader.getCursor(), "Can not mix local coordinates (^) with world coordinates!");
		}
		relative = prefix == '~';
		if (isLocal || relative)
			reader.skip();
	}

	private boolean hasValue() {
		return reader.canRead() && !Character.isWhitespace(reader.peek());
	}

	private void finish() {
		index++;
		if (index < count) {
			if (!reader.canRead() || reader.peek() != ' ')
				throw new CommandSyntaxException(reader.getCommand(), start, "Expected " + count + " " + type + " arguments");
			reader.skip();
		} else if (hasValue()) {
			throw new CommandSyntaxException(reader.getCommand(), reader.getCursor(), "Expected whitespace or end after " + count + " " + type + " arguments");
		}
	}

}
