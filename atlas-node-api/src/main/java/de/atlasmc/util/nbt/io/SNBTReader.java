package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.Reader;
import java.util.UUID;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.regex.Pattern;

import de.atlasmc.util.map.key.CharKeyBuffer;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;

/**
 * {@link NBTReader} implementation to read NBT Json<br>
 * Only able to detect Compound, String, Number, List/Array
 */
public class SNBTReader implements NBTReader {
	
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
	
	private TagType type;
	private int depth;
	private int arrayTagPayload = -1;
	private ListData list;
	private Reader reader;
	private CharKeyBuffer buffer;
	private CharKeyBuffer name;
	private boolean closed;
	private boolean hasName;
	
	public SNBTReader(Reader in) throws IOException {
		if (in == null)
			throw new IllegalArgumentException("Reader can not be null!");
		reader = in;
		prepareTag();
	}

	@Override
	public void close() throws IOException {
		if (closed)
			return;
		closed = true;
		reader.close();
		reader = null;
		name = null;
		buffer = null;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public CharSequence getFieldName() {
		return name.getView();
	}
	
	private String getFieldNameString() {
		return hasName ? name.toString() : null;
	}

	@Override
	public TagType getListType() {
		return list == null ? null : list.type;
	}

	@Override
	public int getRestPayload() {
		if (list == null || list.depth != depth) 
			return 0;
		return list.payload;
	}
	
	@Override
	public int getNextPayload() {
		if (list == null || list.depth >= depth)
			return 0;
		return list.depth;
	}

	@Override
	public TagType getType() {
		return type;
	}

	@Override
	public boolean isArrayTag() {
		return type == TagType.BYTE_ARRAY ||
				type == TagType.INT_ARRAY ||
				type == TagType.LONG_ARRAY;
	}

	@Override
	public int getArrayTagPayload() {
		return arrayTagPayload;
	}

	@Override
	public void readByteArrayTag(IntConsumer dataConsumer) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int readByteArrayTag(byte[] buf) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] readByteArrayTag() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte readByteTag() throws IOException {
		return (byte) readIntTag();
	}

	@Override
	public double readDoubleTag() throws IOException {
		ensureOpen();
		double value = Double.parseDouble(buffer.toString());
		prepareTag();
		return value;
	}

	@Override
	public float readFloatTag() throws IOException {
		ensureOpen();
		float value = Float.parseFloat(buffer.toString());
		prepareTag();
		return value;
	}

	@Override
	public void readIntArrayTag(IntConsumer dataConsumer) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int readIntArrayTag(int[] buf) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] readIntArrayTag() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int readIntTag() throws IOException {
		ensureOpen();
		int value = Integer.parseInt(buffer, 0, buffer.length(), 10);
		prepareTag();
		return value;
	}

	@Override
	public void readLongArrayTag(LongConsumer dataConsumer) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int readLongArrayTag(long[] buf) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long[] readLongArrayTag() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long readLongTag() throws IOException {
		ensureOpen();
		long value = Long.parseLong(buffer, 0, buffer.length(), 10);
		prepareTag();
		return value;
	}

	@Override
	public NBT readNBT() throws IOException {
		ensureOpen();
		final boolean isList = list != null && depth == list.depth;
		switch (isList ? list.type : type) {
		case BYTE: return NBT.createByteTag(getFieldNameString(), readByteTag());
		case BYTE_ARRAY: return NBT.createByteArrayTag(getFieldNameString(), readByteArrayTag());
		case COMPOUND: {
			if (isList) {
				final ListTag<CompoundTag> list = new ListTag<>(getFieldNameString(), this.list.type);
				readNextEntry(); // move out of list header
				while (this.list.payload > 0) {
					CompoundTag compound = new CompoundTag(getFieldNameString());
					final int depth = getDepth(); // root depth of compound
					while (depth <= getDepth()) {
						if (type == TagType.TAG_END) {
							readNextEntry(); // move out of list or to next compound in list
							break;
						}
						compound.addTag(readNBT());
					}
					list.addTag(compound);
				}
				return list;
			}
			final CompoundTag compound = new CompoundTag(getFieldNameString());
			readNextEntry(); // move to first compound entry
			final int depth = getDepth(); // root depth of compound
			while (depth <= getDepth()) {
				if (type == TagType.TAG_END) {
					readNextEntry(); // skip end
					break;
				}
				compound.addTag(readNBT());
			}
			return compound;
		}
		case DOUBLE: return NBT.createDoubleTag(getFieldNameString(), readDoubleTag());
		case FLOAT: return NBT.createFloatTag(getFieldNameString(), readFloatTag());
		case INT: return NBT.createIntTag(getFieldNameString(), readIntTag());
		case INT_ARRAY: return NBT.createIntArrayTag(getFieldNameString(), readIntArrayTag());
		case LIST: 
			if (isList) {
				ListTag<NBT> list = new ListTag<>(getFieldNameString(), TagType.LIST);
				readNextEntry();
				while (this.list.payload > 0) {
					list.addTag(readNBT());
				}
				return list;
			}
			final ListTag<NBT> list = new ListTag<>(getFieldNameString(), this.list.type);
			resetName();
			while (getRestPayload() > 0) {
				list.addTag(readNBT());
			}
			return list;
		case LONG: return NBT.createLongTag(getFieldNameString(), readLongTag());
		case LONG_ARRAY: return NBT.createLongArrayTag(getFieldNameString(), readLongArrayTag());
		case SHORT: return NBT.createShortTag(getFieldNameString(), readShortTag());
		case STRING: return NBT.createStringTag(getFieldNameString(), readStringTag());
		case TAG_END: readNextEntry(); return null;
		default:
			throw new NBTException("Error while reading NBT: isList=" + isList + " Type=" + type.name() + " ListType=" + (isList ? this.list.type.name() : "null"));
		}
	}

	@Override
	public void readNextEntry() throws IOException {
		ensureOpen();
		if (type == TagType.LIST) {
			if (list.type == TagType.COMPOUND) {
				depth += 2;
				prepareTag();
				return;
			} else if (list.type == TagType.LIST) {
				depth++;
				//addList();
				return;
			}
		} else if (type == TagType.TAG_END) {
			if (list != null && list.depth == depth && list.type == TagType.COMPOUND && list.payload > 0)
				depth++;
			prepareTag();
			return;
		} else if (type == TagType.COMPOUND) {
			prepareTag();
			return;
		}
		throw new IOException("Next entry should only be called on LIST, COMPOUND or END: " + type.name());
	}

	@Override
	public short readShortTag() throws IOException {
		return (short) readIntTag();
	}

	@Override
	public String readStringTag() throws IOException {
		ensureOpen();
		String value = buffer.toString();
		prepareTag();
		return value;
	}

	@Override
	public UUID readUUID() throws IOException {
		ensureOpen();
		int[] values = readIntArrayTag();
		if (values.length != 4) 
			throw new NBTException("Invalid UUID data length: " + values.length);
		long most = (values[0] << 32) | values[1];
		long least = (values[2] << 32) | values[3];
		return new UUID(most, least);
	}

	@Override
	public void skipTag() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skipToEnd() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mark() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void search(CharSequence key, TagType type, boolean list) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	private void prepareTag() throws IOException {
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
	
	protected void resetName() {
		name.clear();
		hasName = false;
	}
	
	protected final void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("Stream closed!");
	}

}
