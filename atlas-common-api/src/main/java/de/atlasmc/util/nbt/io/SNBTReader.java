package de.atlasmc.util.nbt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.regex.Pattern;

import de.atlasmc.util.map.key.CharKeyBuffer;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;

/**
 * {@link NBTReader} implementation to read NBT Json
 */
public class SNBTReader extends AbstractNBTStreamReader {
	
	/**
	 * Pattern for {@link TagType#DOUBLE} without suffix
	 * optional prefixed with +/-
	 * numbers. or optional number . numbers
	 * optional e followed by numbers optional prefixed with +/-
	 */
	private static final Pattern DOUBLE_NOT_SUFFIXED_PATTERN = Pattern.compile("[+-]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Pattern for {@link TagType#DOUBLE} with suffix
	 * optional prefixed with +/-
	 * numbers. or optional number . numbers
	 * optional e followed by numbers optional prefixed with +/-
	 * suffixed with 'd'
	 */
	private static final Pattern DOUBLE_PATTERN = Pattern.compile("[+-]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Pattern for {@link TagType#FLOAT}
	 * optional prefixed with +/-
	 * numbers. or optional number . numbers
	 * optional e followed by numbers optional prefixed with +/-
	 * suffixed with 'f'
	 */
	private static final Pattern FLOAT_PATTERN = Pattern.compile("[+-]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Pattern for {@link TagType#LONG}
	 * optional prefixed with +/-
	 * 0 or any number combination not starting with 0
	 * suffixed with 'l'
	 */
	private static final Pattern LONG_PATTERN = Pattern.compile("[+-]?(?:0|[1-9][0-9]*)l", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Pattern for {@link TagType#INT}
	 * optional prefixed with +/-
	 * 0 or any number combination not starting with 0
	 */
	private static final Pattern INT_PATTERN = Pattern.compile("[+-]?(?:0|[1-9][0-9]*)");
	
	/**
	 * Pattern for {@link TagType#SHORT}
	 * optional prefixed with +/-
	 * 0 or any number combination not starting with 0
	 * suffixed with 's'
	 */
	private static final Pattern SHORT_PATTERN = Pattern.compile("[+-]?(?:0|[1-9][0-9]*)s", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Pattern for {@link TagType#BYTE}
	 * optional prefixed with +/-
	 * 0 or any number combination not starting with 0
	 * suffixed with 'b'
	 */
	private static final Pattern BYTE_PATTERN = Pattern.compile("[+-]?(?:0|[1-9][0-9]*)b", Pattern.CASE_INSENSITIVE);
	
	private BufferedReader reader;
	private CharKeyBuffer buffer;
	private boolean closed;
	
	public SNBTReader(Reader in) {
		if (in == null)
			throw new IllegalArgumentException("Reader can not be null!");
		if (in instanceof BufferedReader reader) {
			this.reader = reader;
		} else {
			this.reader = new BufferedReader(in);
		}
	}
	
	@Override
	public void close() {
		if (closed)
			return;
		super.close();
		try {
			reader.close();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		reader = null;
		buffer = null;
	}

	@Override
	public void readByteArrayTag(IntConsumer dataConsumer) throws IOException {
		prepareTag();
		ensureTag(TagType.BYTE_ARRAY);
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		final int payload = arrayTagPayload;
		for (int i = 0; i < payload; i++)
			dataConsumer.accept(ioReadByte());
		tagConsumed();
	}

	@Override
	public int readByteArrayTag(byte[] buf) throws IOException {
		prepareTag();
		ensureTag(TagType.BYTE_ARRAY);
		int bytesRead = 0;
		for (int i = 0; i < buf.length; i++) {
			if (arrayTagPayload <= 0)
				break;
			bytesRead++;
			arrayTagPayload--;
			buf[i] = ioReadByte();
		}
		if (arrayTagPayload <= 0)
			tagConsumed();
		return bytesRead;
	}

	@Override
	public byte[] readByteArrayTag() throws IOException {
		prepareTag();
		ensureTag(TagType.BYTE_ARRAY);
		byte[] data = new byte[arrayTagPayload];
		for (int i = 0; i < arrayTagPayload; i++)
			data[i] = ioReadByte();
		tagConsumed();
		return data;
	}

	@Override
	public byte readByteTag() throws IOException {
		prepareTag();
		byte data = 0;
		if (type == TagType.BYTE) {
			data = ioReadByte();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case SHORT:
				data = (byte) ioReadShort();
				break;
			case LONG:
				data = (byte) ioReadLong();
				break;
			case FLOAT:
				data = (byte) ioReadFloat();
				break;
			case DOUBLE:
				data = (byte) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}
	
	@Override
	public double readDoubleTag() throws IOException {
		prepareTag();
		double data = 0;
		if (type == TagType.DOUBLE) {
			data = ioReadDouble();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadDouble();
				break;
			case SHORT:
				data = ioReadShort();
				break;
			case INT:
				data = ioReadInt();
				break;
			case LONG:
				data = ioReadLong();
				break;
			case FLOAT:
				data = ioReadFloat();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public float readFloatTag() throws IOException {
		prepareTag();
		float data = 0;
		if (type == TagType.FLOAT) {
			data = ioReadFloat();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case SHORT:
				data = ioReadShort();
				break;
			case INT:
				data = ioReadInt();
				break;
			case LONG:
				data = ioReadLong();
				break;
			case DOUBLE:
				data = (float) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public void readIntArrayTag(IntConsumer dataConsumer) throws IOException {
		prepareTag();
		ensureTag(TagType.INT_ARRAY);
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		final int payload = arrayTagPayload;
		for (int i = 0; i < payload; i++)
			dataConsumer.accept(ioReadInt());
		tagConsumed();
	}

	@Override
	public int readIntArrayTag(int[] buf) throws IOException {
		prepareTag();
		ensureTag(TagType.INT_ARRAY);
		int intsRead = 0;
		for (int i = 0; i < buf.length; i++) {
			if (arrayTagPayload <= 0)
				break;
			intsRead++;
			arrayTagPayload--;
			buf[i] = ioReadInt();
		}
		if (arrayTagPayload <= 0)
			tagConsumed();
		return intsRead;
	}

	@Override
	public int[] readIntArrayTag() throws IOException {
		prepareTag();
		ensureTag(TagType.INT_ARRAY);
		int[] data = new int[arrayTagPayload];
		for (int i = 0; i < data.length; i++) {
			data[i] = ioReadInt();
		}
		tagConsumed();
		return data;
	}

	@Override
	public int readIntTag() throws IOException {
		prepareTag();
		int data = 0;
		if (type == TagType.INT) {
			data = ioReadInt();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case SHORT:
				data = ioReadShort();
				break;
			case LONG:
				data = (int) ioReadLong();
				break;
			case FLOAT:
				data = (int) ioReadFloat();
				break;
			case DOUBLE:
				data = (int) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public void readLongArrayTag(LongConsumer dataConsumer) throws IOException {
		prepareTag();
		ensureTag(TagType.LONG_ARRAY);
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		final int payload = arrayTagPayload;
		for (int i = 0; i < payload; i++)
			dataConsumer.accept(ioReadLong());
		tagConsumed();
	}

	@Override
	public int readLongArrayTag(long[] buf) throws IOException {
		prepareTag();
		ensureTag(TagType.LONG_ARRAY);
		int longsRead = 0;
		for (int i = 0; i < buf.length; i++) {
			if (arrayTagPayload <= 0)
				break;
			longsRead++;
			arrayTagPayload--;
			buf[i] = ioReadLong();
		}
		if (arrayTagPayload <= 0)
			tagConsumed();
		return longsRead;
	}

	@Override
	public long[] readLongArrayTag() throws IOException {
		prepareTag();
		ensureTag(TagType.LONG_ARRAY);
		long[] data = new long[arrayTagPayload];
		for (int i = 0; i < data.length; i++) {
			data[i] = ioReadLong();
		}
		tagConsumed();
		return data;
	}

	@Override
	public long readLongTag() throws IOException {
		prepareTag();
		long data = 0;
		if (type == TagType.LONG) {
			data = ioReadLong();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case SHORT:
				data = ioReadShort();
				break;
			case INT:
				data = ioReadInt();
				break;
			case FLOAT:
				data = (long) ioReadFloat();
				break;
			case DOUBLE:
				data = (long) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public short readShortTag() throws IOException {
		prepareTag();
		short data = 0;
		if (type == TagType.SHORT) {
			data = ioReadShort();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case INT:
				data = (short) ioReadInt();
				break;
			case LONG:
				data = (short) ioReadLong();
				break;
			case FLOAT:
				data = (short) ioReadFloat();
				break;
			case DOUBLE:
				data = (short) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}


	@Override
	public String readStringTag() throws IOException {
		prepareTag();
		ensureTag(TagType.STRING);
		String value = buffer.toString();
		tagConsumed();
		return value;
	}
	
	@Override
	protected void prepareTag(boolean skip) throws IOException {
		buffer.clear();
		int c = skipWhitespaces();
		if (c == -1)
			return;
		if (c == '"')
			readEscapedKey();
		else
			readKey(c);
		
	}
	
	private void readEscapedKey() {
		
	}
	
	private void readKey(int c) {
		
	}
	
	@SuppressWarnings("unlikely-arg-type")
	private TagType identifySimpleType() {
		if (DOUBLE_PATTERN.matcher(buffer).matches()) {
			buffer.setLength(buffer.length()-1);
			return TagType.DOUBLE;
		} else if (FLOAT_PATTERN.matcher(buffer).matches()) {
			buffer.setLength(buffer.length()-1);
			return TagType.FLOAT;
		} else if (LONG_PATTERN.matcher(buffer).matches()) {
			buffer.setLength(buffer.length()-1);
			return TagType.LONG;
		} else if (SHORT_PATTERN.matcher(buffer).matches()) {
			buffer.setLength(buffer.length()-1);
			return TagType.SHORT;
		} else if (BYTE_PATTERN.matcher(buffer).matches()) {
			buffer.setLength(buffer.length()-1);
			return TagType.BYTE;
		} else if (DOUBLE_NOT_SUFFIXED_PATTERN.matcher(buffer).matches()) {
			return TagType.DOUBLE;
		} else if (INT_PATTERN.matcher(buffer).matches()) {
			return TagType.INT;
		} else if (buffer.equals(Boolean.toString(true))) {
			buffer.setLength(0);
			buffer.append('1');
			return TagType.BYTE;
		} else if (buffer.equals(Boolean.toString(false))) {
			buffer.setLength(0);
			buffer.append('0');
			return TagType.BYTE;
		} return TagType.STRING;
	}
	
	private boolean find(char charr) throws IOException {
		int read = -1;
		while ((read = reader.read()) != charr) {
			if (read == -1) // End of stream
				return false;
		}
		return true;
	}
	
	/**
	 * Skips all white spaces and returns the next read char or -1 if end of stream
	 * @return char or -1
	 * @throws IOException 
	 */
	private int skipWhitespaces() throws IOException {
		int next = -1;
		while (Character.isWhitespace(next = reader.read()));
		return next;
	}

	@Override
	protected void skipTag(boolean skip) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	private byte ioReadByte() {
		return 0;
	}
	
	private int ioReadInt() {
		return 0;
	}
	
	private short ioReadShort() {
		return 0;
	}
	
	private float ioReadFloat() {
		return 0;
	}
	
	private double ioReadDouble() {
		return 0;
	}
	
	private long ioReadLong() {
		return 0;
	}
	
}
