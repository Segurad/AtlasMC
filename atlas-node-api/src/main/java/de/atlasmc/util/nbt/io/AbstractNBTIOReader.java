package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.UUID;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import de.atlasmc.util.map.key.CharKeyBuffer;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;

public abstract class AbstractNBTIOReader implements NBTReader {
	
	private TagType type;
	private TagType markType;
	private CharKeyBuffer name;
	private boolean prepared;
	private boolean markPrepared;
	private boolean hasName;
	private String markName;
	private int depth = -1;
	private int markDepth;
	private ListData list;
	private ListData markList;
	private boolean closed;
	private int arrayTagPayload = -1;
	private int markArrayTagPayload = -1;
	private final boolean unnamedRoot;
	
	public AbstractNBTIOReader(boolean unnamedRoot) {
		name = new CharKeyBuffer();
		this.unnamedRoot = unnamedRoot;
	}
	
	@Override
	public int getDepth() throws IOException {
		prepareTag();
		return depth;
	}
	
	@Override
	public CharSequence getFieldName() throws IOException {
		prepareTag();
		return hasName ? name.getView() : null;
	}
	
	private String getFieldNameString() throws IOException {
		prepareTag();
		return hasName ? name.toString() : null;
	}
	
	@Override
	public TagType getListType() throws IOException {
		prepareTag();
		return list == null ? null : list.type;
	}
	
	@Override
	public int getRestPayload() throws IOException {
		prepareTag();
		if (list == null || list.depth != depth) 
			return 0;
		return list.payload;
	}
	
	@Override
	public int getNextPayload() throws IOException {
		prepareTag();
		if (list == null || list.depth >= depth)
			return 0;
		return list.depth;
	}
	
	@Override
	public TagType getType() throws IOException {
		prepareTag();
		return type;
	}
	
	private void tagConsumed() {
		prepared = false;
		arrayTagPayload = -1;
		if (list != null && (depth == list.depth || (list.type == TagType.COMPOUND && list.depth + 1 == depth))) {
			resetName();
			if (list.payload > 0) {
				list.payload--;
				if (list.payload <= 0)
					removeList();
			}
			return;
		}
	}
	
	@Override
	public void readByteArrayTag(IntConsumer dataConsumer) throws IOException {
		prepareTag();
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
		byte[] data = new byte[arrayTagPayload];
		ioReadBytes(data);
		tagConsumed();
		return data;
	}
	
	@Override
	public byte readByteTag() throws IOException {
		prepareTag();
		byte data = ioReadByte();
		tagConsumed();
		return data;
	}
	
	@Override
	public double readDoubleTag() throws IOException {
		prepareTag();
		double data = ioReadDouble();
		tagConsumed();
		return data;
	}
	
	@Override
	public float readFloatTag() throws IOException {
		ensureOpen();
		float data = ioReadFloat();
		prepareTag();
		return data;
	}
	
	@Override
	public void readIntArrayTag(IntConsumer dataConsumer) throws IOException {
		prepareTag();
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
		int data = ioReadInt();
		tagConsumed();
		return data;
	}
	
	@Override
	public void readLongArrayTag(LongConsumer dataConsumer) throws IOException {
		prepareTag();
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
		long data = ioReadLong();
		tagConsumed();
		return data;
	}
	
	@Override
	public short readShortTag() throws IOException {
		prepareTag();
		short data = ioReadShort();
		tagConsumed();
		return data;
	}
	
	@Override
	public String readStringTag() throws IOException {
		prepareTag();
		byte[] buffer = new byte[ioReadShort()];
		ioReadBytes(buffer);
		tagConsumed();
		return new String(buffer);
	}
	
	protected void prepareTag() throws IOException {
		prepareTag(false);
	}
	
	protected void prepareTag(boolean skip) throws IOException {
		ensureOpen();
		if (prepared)
			return;
		prepared = true;
		type = TagType.getByID(ioReadByte());
		if (type == TagType.TAG_END) {
			resetName();
			depth--;
			if (list != null && depth == list.depth) 
				type = TagType.LIST;
		} else if (!unnamedRoot || depth != -1) {
			if (!skip) {
				readName(name);
				hasName = true;
			} else {
				// skip name of tag
				skipBytes(ioReadShort());
			}
		}
		if (type == TagType.LIST) 
			addList();
		else if (isArrayTag())
			arrayTagPayload = ioReadInt();
	}
	
	protected void resetName() {
		name.clear();
		hasName = false;
	}

	@Override
	public void readNextEntry() throws IOException {
		readNextEntry(false);
	}
	
	private void readNextEntry(boolean skipPrepare) throws IOException {
		prepareTag(skipPrepare);
		if (type == TagType.LIST) {
			if (list.type == TagType.COMPOUND) {
				depth += 2;
				tagConsumed();
				return;
			} else if (list.type == TagType.LIST) {
				depth++;
				addList();
				return;
			}
		} else if (type == TagType.TAG_END) {
			if (list != null && list.depth == depth && list.type == TagType.COMPOUND && list.payload > 0)
				depth++;
			tagConsumed();
			return;
		} else if (type == TagType.COMPOUND) {
			tagConsumed();
			return;
		}
		throw new IOException("Next entry should only be called on LIST, COMPOUND or END: " + type.name());
	}
	
	@Override
	public UUID readUUID() throws IOException {
		ensureOpen();
		int length = ioReadInt();
		if (length != 4) 
			throw new NBTException("Invalid UUID data length: " + length);
		return new UUID(ioReadLong(), ioReadLong());
	}
	
	private void addList() throws IOException {
		ensureOpen();
		TagType type = TagType.getByID(ioReadByte());
		int payload = ioReadInt();
		if (payload <= 0) {
			prepareTag();
			return;
		}
		list = new ListData(type, payload, depth + 1, list);
		tagConsumed();
	}

	private void removeList() {
		if (list == null) 
			return;
		list = list.parent;
		depth--;
		if (list != null && depth == list.depth) 
			type = TagType.LIST;
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
	public void skipTag() throws IOException {
		skipTag(false);
	}
	
	protected void skipTag(boolean skipPrepare) throws IOException {
		prepareTag();
		final boolean isList = list != null && depth == list.depth;
		switch (isList ? list.type : type) {
		case BYTE: skipTag(1, skipPrepare); break;
		case BYTE_ARRAY: skipTag(arrayTagPayload, skipPrepare); break;
		case COMPOUND: 
			if (isList) {
				readNextEntry(true); // move out of list header
				while (list.payload > 0) {
					final int depth = getDepth(); // root depth of compound
					while (depth <= getDepth()) {
						if (type == TagType.TAG_END) {
							readNextEntry(true); // move out of list or to next compound in list
							break;
						}
						skipTag(true);
					}
					list.payload--;
				}
				removeList();
				return;
			}
			readNextEntry(true); // move to first compound entry
			final int depth = getDepth(); // root depth of compound
			while (depth <= getDepth()) {
				if (type == TagType.TAG_END) {
					readNextEntry(true); // skip end
					break;
				}
				skipTag(true);
			}
		case DOUBLE: skipTag(8, skipPrepare); break;
		case FLOAT: skipTag(4, skipPrepare); break;
		case INT: skipTag(4, skipPrepare); break;
		case INT_ARRAY: skipTag(arrayTagPayload*4, skipPrepare); break;
		case LIST: 
			if (isList) {
				readNextEntry(true);
				while (list.payload > 0) {
					skipTag(true);
					list.payload--;
				}
				removeList();
				return;
			}
			while (getRestPayload() > 0) {
				skipTag(true);
			}
			break;
		case LONG: skipTag(8, skipPrepare); break;
		case LONG_ARRAY: skipTag(arrayTagPayload*8, skipPrepare);
		case SHORT: skipTag(2, skipPrepare); break;
		case STRING: skipTag(ioReadShort(), skipPrepare); break;
		case TAG_END: readNextEntry(skipPrepare); break;
		}
	}
	
	@Override
	public void skipToEnd() throws IOException {
		final int depth = getDepth();
		while (depth <= getDepth()) {
			if (getType() == TagType.TAG_END && depth == getDepth())
				readNextEntry();
			else
				skipTag(true);
		}
	}
	
	private void skipTag(int bytes, boolean skipPrepare) throws IOException {
		skipBytes(bytes);
		tagConsumed();
	}

	@Override
	public void mark() {
		markDepth = depth;
		if (list != null) {
			markList = list;
			do {
				list.markPayload = list.payload;
				list = list.parent;
			} while (list != null);
			list = markList;
		}
		markName = name.toString();
		markType = type;
		markArrayTagPayload = arrayTagPayload;
		markPrepared = prepared;
		ioMark();
	}

	@Override
	public void reset() {
		depth = markDepth;
		list = markList;
		markList = null;
		name.clear();
		name.append(markName);
		markName = null;
		type = markType;
		arrayTagPayload = markArrayTagPayload;
		markArrayTagPayload = -1;
		prepared = markPrepared;
		ioReset();
	}

	@Override
	public void search(CharSequence key, TagType stype, boolean slist) throws IOException {
		ensureOpen();
		final int depth = getDepth();
		while (depth <= getDepth()) {
			// check if current tag is the result
			if ((key == null || name.equals(key)) && 
					(stype == null || !slist ? stype == type 
					: type == TagType.LIST && stype == list.type)) break; // breaks if search result == true
			// --- Skip to next ---
			// Handle Compound, List(Compound) and List(List)
			if (type == TagType.COMPOUND || (type == TagType.LIST && (list.type == TagType.COMPOUND || list.type == TagType.LIST))) {
				readNextEntry(); // progress to first element of list or compound
			} else skipTag(); // progress to next
		}
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
	
	public void close() {
		closed = true;
		name = null;
		hasName = false;
		markName = null;
		depth = Integer.MIN_VALUE;
		markDepth = depth;
		list = null;
		markList = null;
	}
	
	protected final void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("Stream closed!");
	}
	
	/*
	 * --- Methods for reading data by subclass ---
	 */
	
	protected void readName(CharKeyBuffer buf) throws IOException {
		buf.clear();
		final int length = ioReadShort() & 0xFFFF;
		for (int i = 0; i < length; i++) {
			int first = ioReadByte();
			if ((first & 0x40) == 0) {
				buf.append((char) (first & 0x7F));
			} else if ((first & 0xE0) == 0xC0) {
				int b = ioReadByte();
				if ((b & 0xC0) != 0x80) 
					throw new UTFDataFormatException();
				buf.append((char) (((first & 0x1F) << 6) | (b & 0x3F)));
			} else if ((first & 0xF0) == 0xE0) {
				int b = ioReadByte();
				if ((b & 0xC0) != 0x80) 
					throw new UTFDataFormatException();
				int c = ioReadByte();
				if ((c & 0xC0) != 0x80) 
					throw new UTFDataFormatException();
				buf.append((char) (((first & 0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F)));
			}
		}
	}
	
	/**
	 * @see #mark()
	 */
	protected abstract void ioMark();
	
	/**
	 * @see #reset()
	 */
	protected abstract void ioReset();
	
	protected abstract int ioReadInt() throws IOException;
	
	protected abstract byte ioReadByte() throws IOException;
	
	protected abstract void ioReadBytes(byte[] buffer) throws IOException;
	
	protected abstract short ioReadShort() throws IOException;
	
	protected abstract long ioReadLong() throws IOException;
	
	protected abstract float ioReadFloat() throws IOException;
	
	protected abstract double ioReadDouble() throws IOException;
	
	protected abstract void skipBytes(int bytes) throws IOException;

}
