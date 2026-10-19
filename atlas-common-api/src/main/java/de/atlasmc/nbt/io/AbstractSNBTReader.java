package de.atlasmc.nbt.io;

import java.io.IOException;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.util.map.key.CharKeyBuffer;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.LongArrayList;

/**
 * Streaming SNBT reader. Parses the input char by char without building an intermediate object tree.
 * <p>
 * SNBT does not contain sizes, therefore {@link #getRestPayload()} and {@link #getArrayTagPayload()}
 * return 1 while more elements may follow and 0 once the end of the list or array was reached.
 * The type of a list is determined by looking ahead at its first element.
 * A empty list has the type {@link TagType#TAG_END}.
 */
public abstract class AbstractSNBTReader extends AbstractNBTStreamReader {

	/**
	 * Holds the raw text of the current string or unquoted value
	 */
	private CharKeyBuffer value = new CharKeyBuffer();
	private long longValue;
	private double doubleValue;
	/**
	 * Whether or not the next entry is the first entry of the current compound or list (no separator expected)
	 */
	private boolean first = true;
	private boolean rootRead;
	/**
	 * Number of consumed chars, used for error messages
	 */
	private int index;

	private String markValue;
	private long markLongValue;
	private double markDoubleValue;
	private boolean markFirst;
	private boolean markRootRead;
	private int markIndex;

	@Override
	protected void prepareTag(boolean skip) throws IOException {
		ensureOpen();
		if (prepared) // avoid preparing again
			return;
		if (isList()) {
			final ListData current = list;
			if (!nextEntry(']')) {
				current.payload = 0;
				type = TagType.LIST; // list end, next call of readNextEntry() will leave the list
				prepared = true;
				return;
			}
			readValue();
			if (type != current.type)
				throw error("List of " + current.type + " may not contain " + type);
		} else if (depth >= 0) { // compound entry
			if (!nextEntry('}')) {
				type = TagType.TAG_END;
				prepared = true;
				return;
			}
			readKey();
			skipWhitespace();
			expect(':');
			readValue();
		} else { // root
			if (rootRead)
				throw new IOException("End of stream reached!");
			rootRead = true;
			hasName = true; // root is named with a empty name like in a nbt stream
			readValue();
		}
		prepared = true;
	}

	/**
	 * Lists are not counted in SNBT the payload of a list is maintained by {@link #prepareTag(boolean)}
	 */
	@Override
	protected void tagConsumed() {
		prepared = false;
		resetName();
		arrayTagPayload = -1;
	}

	@Override
	protected void readNextEntry(boolean skip) throws IOException {
		prepareTag(skip);
		final boolean enter = type == TagType.COMPOUND || (type == TagType.LIST && !list.entered);
		super.readNextEntry(skip);
		if (enter)
			first = true;
	}

	@Override
	protected void skipTag(boolean skip) throws IOException {
		prepareTag(skip);
		switch (type) {
		case COMPOUND:
			readNextEntry(true);
			while (getType() != TagType.TAG_END)
				skipTag(true);
			readNextEntry(true);
			break;
		case LIST:
			if (list.entered) { // end of current list
				readNextEntry(true);
				break;
			}
			readNextEntry(true);
			while (getRestPayload() > 0)
				skipTag(true);
			readNextEntry(true);
			break;
		case TAG_END:
			readNextEntry(true);
			break;
		case BYTE_ARRAY, INT_ARRAY, LONG_ARRAY:
			while (arrayTagPayload > 0)
				readArrayElement(TagType.LONG);
			tagConsumed();
			break;
		default: // values are already read by prepare
			tagConsumed();
		}
	}

	/*
	 * --- Value reading ---
	 */

	@Override
	public byte readByteTag() throws IOException {
		return (byte) readIntegral();
	}

	@Override
	public short readShortTag() throws IOException {
		return (short) readIntegral();
	}

	@Override
	public int readIntTag() throws IOException {
		return (int) readIntegral();
	}

	@Override
	public long readLongTag() throws IOException {
		return readIntegral();
	}

	@Override
	public float readFloatTag() throws IOException {
		return (float) readFloating();
	}

	@Override
	public double readDoubleTag() throws IOException {
		return readFloating();
	}

	private long readIntegral() throws IOException {
		prepareTag();
		final TagType type = this.type;
		ensureNumberTag(type);
		final long data = type == TagType.FLOAT || type == TagType.DOUBLE ? (long) doubleValue : longValue;
		tagConsumed();
		return data;
	}

	private double readFloating() throws IOException {
		prepareTag();
		final TagType type = this.type;
		ensureNumberTag(type);
		final double data = type == TagType.FLOAT || type == TagType.DOUBLE ? doubleValue : longValue;
		tagConsumed();
		return data;
	}

	@Override
	public String readStringTag() throws IOException {
		prepareTag();
		ensureTag(TagType.STRING);
		final String data = value.toString();
		tagConsumed();
		return data;
	}

	@Override
	public void readByteArrayTag(IntConsumer consumer) throws IOException {
		prepareTag();
		ensureTag(TagType.BYTE_ARRAY);
		Objects.requireNonNull(consumer, "consumer");
		while (arrayTagPayload > 0)
			consumer.accept((byte) readArrayElement(TagType.BYTE));
		tagConsumed();
	}

	@Override
	public int readByteArrayTag(byte[] buf) throws IOException {
		prepareTag();
		ensureTag(TagType.BYTE_ARRAY);
		int read = 0;
		while (read < buf.length && arrayTagPayload > 0)
			buf[read++] = (byte) readArrayElement(TagType.BYTE);
		if (arrayTagPayload <= 0)
			tagConsumed();
		return read;
	}

	@Override
	public byte[] readByteArrayTag() throws IOException {
		prepareTag();
		ensureTag(TagType.BYTE_ARRAY);
		ByteArrayList data = new ByteArrayList();
		while (arrayTagPayload > 0)
			data.add((byte) readArrayElement(TagType.BYTE));
		tagConsumed();
		return data.toByteArray();
	}

	@Override
	public void readIntArrayTag(IntConsumer consumer) throws IOException {
		prepareTag();
		ensureTag(TagType.INT_ARRAY);
		Objects.requireNonNull(consumer, "consumer");
		while (arrayTagPayload > 0)
			consumer.accept((int) readArrayElement(TagType.INT));
		tagConsumed();
	}

	@Override
	public int readIntArrayTag(int[] buf) throws IOException {
		prepareTag();
		ensureTag(TagType.INT_ARRAY);
		int read = 0;
		while (read < buf.length && arrayTagPayload > 0)
			buf[read++] = (int) readArrayElement(TagType.INT);
		if (arrayTagPayload <= 0)
			tagConsumed();
		return read;
	}

	@Override
	public int[] readIntArrayTag() throws IOException {
		prepareTag();
		ensureTag(TagType.INT_ARRAY);
		IntArrayList data = new IntArrayList();
		while (arrayTagPayload > 0)
			data.add((int) readArrayElement(TagType.INT));
		tagConsumed();
		return data.toIntArray();
	}

	@Override
	public void readLongArrayTag(LongConsumer consumer) throws IOException {
		prepareTag();
		ensureTag(TagType.LONG_ARRAY);
		Objects.requireNonNull(consumer, "consumer");
		while (arrayTagPayload > 0)
			consumer.accept(readArrayElement(TagType.LONG));
		tagConsumed();
	}

	@Override
	public int readLongArrayTag(long[] buf) throws IOException {
		prepareTag();
		ensureTag(TagType.LONG_ARRAY);
		int read = 0;
		while (read < buf.length && arrayTagPayload > 0)
			buf[read++] = readArrayElement(TagType.LONG);
		if (arrayTagPayload <= 0)
			tagConsumed();
		return read;
	}

	@Override
	public long[] readLongArrayTag() throws IOException {
		prepareTag();
		ensureTag(TagType.LONG_ARRAY);
		LongArrayList data = new LongArrayList();
		while (arrayTagPayload > 0)
			data.add(readArrayElement(TagType.LONG));
		tagConsumed();
		return data.toLongArray();
	}

	/*
	 * --- Parsing ---
	 */

	/**
	 * Moves to the next entry of the current compound or list
	 * @param close the closing char of the current compound or list
	 * @return true if a entry follows or false if the closing char was consumed
	 * @throws IOException
	 */
	private boolean nextEntry(char close) throws IOException {
		skipWhitespace();
		if (first) {
			first = false;
			if (peek(0) != close)
				return true;
			read();
			return false;
		}
		final int c = read();
		if (c == close)
			return false;
		if (c != ',')
			throw unexpected(c, "',' or '" + close + "'");
		skipWhitespace();
		return true;
	}

	private void readKey() throws IOException {
		final int c = peek(0);
		if (c == '"' || c == '\'') {
			readQuoted(name);
		} else {
			readUnquoted(name);
			if (name.length() == 0)
				throw unexpected(c, "key");
		}
		hasName = true;
	}

	/**
	 * Reads the header of the next value and sets the type.
	 * Scalar values are read completely, for compounds and lists only the opening bracket is consumed.
	 * @throws IOException
	 */
	private void readValue() throws IOException {
		skipWhitespace();
		final int c = peek(0);
		switch (c) {
		case '{':
			read();
			type = TagType.COMPOUND;
			break;
		case '[': {
			final TagType arrayType = peekArrayType(0);
			if (arrayType != null) {
				read(); // [
				read(); // B, I or L
				read(); // ;
				type = arrayType;
				skipWhitespace();
				if (peek(0) == ']') {
					read();
					arrayTagPayload = 0;
				} else {
					arrayTagPayload = 1;
				}
				break;
			}
			read();
			addList(peekListType(), 1);
			type = TagType.LIST;
			break;
		}
		case '"', '\'':
			value.clear();
			readQuoted(value);
			type = TagType.STRING;
			break;
		default:
			value.clear();
			readUnquoted(value);
			if (value.length() == 0)
				throw unexpected(c, "value");
			type = classifyUnquoted();
		}
	}

	/**
	 * Returns the array type if the input at the given offset starts with [B; [I; or [L;
	 */
	private TagType peekArrayType(int offset) throws IOException {
		if (peek(offset + 2) != ';')
			return null;
		return switch (peek(offset + 1)) {
		case 'B' -> TagType.BYTE_ARRAY;
		case 'I' -> TagType.INT_ARRAY;
		case 'L' -> TagType.LONG_ARRAY;
		default -> null;
		};
	}

	/**
	 * Determines the type of the first list element without consuming it
	 */
	private TagType peekListType() throws IOException {
		skipWhitespace();
		final int c = peek(0);
		switch (c) {
		case ']':
			return TagType.TAG_END;
		case '{':
			return TagType.COMPOUND;
		case '[': {
			final TagType arrayType = peekArrayType(0);
			return arrayType != null ? arrayType : TagType.LIST;
		}
		case '"', '\'':
			return TagType.STRING;
		default:
			final CharKeyBuffer value = this.value;
			value.clear();
			int i = 0;
			int next;
			while (isUnquotedChar(next = peek(i))) {
				value.append((char) next);
				i++;
			}
			if (i == 0)
				throw unexpected(c, "value");
			return classifyUnquoted();
		}
	}

	/**
	 * Reads the next element of the current array and moves behind the following separator.
	 * Sets {@link #arrayTagPayload} to 0 if the end of the array was reached.
	 * @param elementType the max type of the element
	 * @return element
	 * @throws IOException
	 */
	private long readArrayElement(TagType elementType) throws IOException {
		skipWhitespace();
		final int c = peek(0);
		value.clear();
		readUnquoted(value);
		if (value.length() == 0)
			throw unexpected(c, "array element");
		final TagType type = classifyUnquoted();
		final long data = longValue;
		final boolean valid = switch (type) {
		case BYTE, SHORT, INT, LONG -> switch (elementType) {
			case BYTE -> data == (byte) data;
			case INT -> data == (int) data;
			default -> true;
			};
		default -> false;
		};
		if (!valid)
			throw error("Invalid " + elementType + " array element: " + value);
		skipWhitespace();
		final int sep = read();
		if (sep == ']') {
			arrayTagPayload = 0;
		} else if (sep != ',') {
			throw unexpected(sep, "',' or ']'");
		}
		return data;
	}

	private void readQuoted(CharKeyBuffer out) throws IOException {
		final int quote = read();
		while (true) {
			int c = read();
			if (c == quote)
				return;
			if (c == -1)
				throw error("Unterminated string");
			if (c == '\\') {
				c = read();
				c = switch (c) {
				case '\\', '"', '\'' -> c;
				case 'n' -> '\n';
				case 't' -> '\t';
				case 'b' -> '\b';
				case 'r' -> '\r';
				case 'f' -> '\f';
				case 's' -> ' ';
				case 'x' -> readHex(2);
				case 'u' -> readHex(4);
				default -> throw unexpected(c, "escape sequence");
				};
			}
			out.append((char) c);
		}
	}

	private int readHex(int digits) throws IOException {
		int value = 0;
		for (int i = 0; i < digits; i++) {
			final int c = read();
			final int digit = c == -1 ? -1 : Character.digit(c, 16);
			if (digit == -1)
				throw unexpected(c, "hex digit");
			value = (value << 4) | digit;
		}
		return value;
	}

	private void readUnquoted(CharKeyBuffer out) throws IOException {
		int c;
		while (isUnquotedChar(c = peek(0))) {
			read();
			out.append((char) c);
		}
	}

	/**
	 * Determines the type of the unquoted {@link #value} and stores numbers in {@link #longValue} or {@link #doubleValue}.
	 * Like vanilla any value that is not a valid number (including out of range numbers) is a string.
	 * @return type
	 */
	private TagType classifyUnquoted() {
		final CharKeyBuffer s = value;
		final int length = s.length();
		if (equalsIgnoreCase(s, "true")) {
			longValue = 1;
			return TagType.BYTE;
		} else if (equalsIgnoreCase(s, "false")) {
			longValue = 0;
			return TagType.BYTE;
		}
		char suffix = Character.toLowerCase(s.charAt(length - 1));
		int end = length;
		switch (suffix) {
		case 'b', 's', 'l', 'f', 'd':
			end--;
			break;
		default:
			suffix = 0;
		}
		int i = 0;
		final char first = s.charAt(0);
		if (first == '+' || first == '-')
			i++;
		final int intStart = i;
		while (i < end && isDigit(s.charAt(i)))
			i++;
		final int intDigits = i - intStart;
		int fracDigits = 0;
		boolean decimal = false;
		if (i < end && s.charAt(i) == '.') {
			decimal = true;
			final int fracStart = ++i;
			while (i < end && isDigit(s.charAt(i)))
				i++;
			fracDigits = i - fracStart;
		}
		if (intDigits + fracDigits == 0)
			return TagType.STRING;
		boolean exponent = false;
		if (i < end && (s.charAt(i) | 0x20) == 'e') {
			exponent = true;
			i++;
			if (i < end && (s.charAt(i) == '+' || s.charAt(i) == '-'))
				i++;
			final int expStart = i;
			while (i < end && isDigit(s.charAt(i)))
				i++;
			if (i == expStart)
				return TagType.STRING;
		}
		if (i != end)
			return TagType.STRING;
		try {
			if (suffix == 'f' || suffix == 'd' || decimal || exponent) {
				if (suffix == 0 && !decimal) // unsuffixed double requires '.'
					return TagType.STRING;
				if (suffix != 0 && suffix != 'f' && suffix != 'd') // e.g. 1.5b
					return TagType.STRING;
				final String raw = s.toString(); // parseDouble accepts the f and d suffix
				if (suffix == 'f') {
					doubleValue = Float.parseFloat(raw);
					return TagType.FLOAT;
				}
				doubleValue = Double.parseDouble(raw);
				return TagType.DOUBLE;
			}
			if (intDigits > 1 && s.charAt(intStart) == '0') // no leading zeros
				return TagType.STRING;
			final long data = Long.parseLong(s, 0, end, 10);
			final TagType type;
			switch (suffix) {
			case 'b':
				if (data != (byte) data)
					return TagType.STRING;
				type = TagType.BYTE;
				break;
			case 's':
				if (data != (short) data)
					return TagType.STRING;
				type = TagType.SHORT;
				break;
			case 'l':
				type = TagType.LONG;
				break;
			default:
				if (data != (int) data)
					return TagType.STRING;
				type = TagType.INT;
			}
			longValue = data;
			return type;
		} catch (NumberFormatException e) {
			return TagType.STRING;
		}
	}

	private static boolean equalsIgnoreCase(CharSequence s, String other) {
		final int length = other.length();
		if (s.length() != length)
			return false;
		for (int i = 0; i < length; i++) {
			if (Character.toLowerCase(s.charAt(i)) != other.charAt(i))
				return false;
		}
		return true;
	}

	private static boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private static boolean isUnquotedChar(int c) {
		return (c >= '0' && c <= '9')
				|| (c >= 'a' && c <= 'z')
				|| (c >= 'A' && c <= 'Z')
				|| c == '_' || c == '-' || c == '.' || c == '+';
	}

	private void skipWhitespace() throws IOException {
		int c;
		while ((c = peek(0)) != -1 && Character.isWhitespace(c))
			read();
	}

	private void expect(char expected) throws IOException {
		final int c = read();
		if (c != expected)
			throw unexpected(c, "'" + expected + "'");
	}

	private int read() throws IOException {
		final int c = ioRead();
		if (c != -1)
			index++;
		return c;
	}

	private int peek(int offset) throws IOException {
		return ioPeek(offset);
	}

	private NBTException unexpected(int c, String expected) {
		return error("Expected " + expected + " but found " + (c == -1 ? "end of input" : "'" + (char) c + "'"));
	}

	private NBTException error(String message) {
		return new NBTException(message + " at index " + index);
	}

	@Override
	public void mark() {
		super.mark();
		markValue = value.toString();
		markLongValue = longValue;
		markDoubleValue = doubleValue;
		markFirst = first;
		markRootRead = rootRead;
		markIndex = index;
		ioMark();
	}

	@Override
	public void reset() {
		super.reset();
		value.clear();
		value.append(markValue);
		markValue = null;
		longValue = markLongValue;
		doubleValue = markDoubleValue;
		first = markFirst;
		rootRead = markRootRead;
		index = markIndex;
		ioReset();
	}

	@Override
	public void close() {
		super.close();
		value = null;
		markValue = null;
	}

	/*
	 * --- Methods for reading data by subclass ---
	 */

	/**
	 * Reads the next char
	 * @return char or -1 if end of input
	 * @throws IOException
	 */
	protected abstract int ioRead() throws IOException;

	/**
	 * Returns the char at the given offset from the current position without consuming it
	 * @param offset
	 * @return char or -1 if end of input
	 * @throws IOException
	 */
	protected abstract int ioPeek(int offset) throws IOException;

	/**
	 * @see #mark()
	 */
	protected abstract void ioMark();

	/**
	 * @see #reset()
	 */
	protected abstract void ioReset();

}
