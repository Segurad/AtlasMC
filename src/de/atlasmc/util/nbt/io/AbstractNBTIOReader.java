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
	private boolean hasName;
	private String markName;
	private int depth;
	private int markDepth;
	private ListData list;
	private ListData markList;
	private boolean closed;
	
	public AbstractNBTIOReader() {
		name = new CharKeyBuffer();
	}
	
	@Override
	public int getDepth() {
		return depth;
	}
	
	@Override
	public CharSequence getFieldName() {
		return hasName ? name.getView() : null;
	}
	
	private String getFieldNameString() {
		return hasName ? name.toString() : null;
	}
	
	@Override
	public TagType getListType() {
		if (list == null) return null;
		return list.type;
	}
	
	@Override
	public int getRestPayload() {
		if (list == null) return 0;
		return list.payload;
	}
	
	@Override
	public TagType getType() {
		return type;
	}
	
	@Override
	public void readByteArrayTag(IntConsumer dataConsumer) throws IOException {
		ensureOpen();
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		int payload = ioReadInt();
		for (int i = 0; i < payload; i++)
			dataConsumer.accept(ioReadByte());
		prepareTag();
	}
	
	@Override
	public byte[] readByteArrayTag() throws IOException {
		ensureOpen();
		byte[] data = new byte[ioReadInt()];
		ioReadBytes(data);
		prepareTag();
		return data;
	}
	
	@Override
	public byte readByteTag() throws IOException {
		ensureOpen();
		byte data = ioReadByte();
		prepareTag();
		return data;
	}
	
	@Override
	public double readDoubleTag() throws IOException {
		ensureOpen();
		double data = ioReadDouble();
		prepareTag();
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
		ensureOpen();
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		int paylaod = ioReadInt();
		for (int i = 0; i < paylaod; i++)
			dataConsumer.accept(ioReadInt());
		prepareTag();
	}
	
	@Override
	public int[] readIntArrayTag() throws IOException {
		ensureOpen();
		int[] data = new int[ioReadInt()];
		for (int i = 0; i < data.length; i++) {
			data[i] = ioReadInt();
		}
		prepareTag();
		return data;
	}
	
	@Override
	public int readIntTag() throws IOException {
		ensureOpen();
		int data = ioReadInt();
		prepareTag();
		return data;
	}
	
	@Override
	public void readLongArrayTag(LongConsumer dataConsumer) throws IOException {
		ensureOpen();
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		int payload = ioReadInt();
		for (int i = 0; i < payload; i++)
			dataConsumer.accept(ioReadLong());
		prepareTag();
	}
	
	@Override
	public long[] readLongArrayTag() throws IOException {
		ensureOpen();
		long[] data = new long[ioReadInt()];
		for (int i = 0; i < data.length; i++) {
			data[i] = ioReadLong();
		}
		prepareTag();
		return data;
	}
	
	@Override
	public long readLongTag() throws IOException {
		ensureOpen();
		long data = ioReadLong();
		prepareTag();
		return data;
	}
	
	@Override
	public short readShortTag() throws IOException {
		ensureOpen();
		short data = ioReadShort();
		prepareTag();
		return data;
	}
	
	@Override
	public String readStringTag() throws IOException {
		ensureOpen();
		byte[] buffer = new byte[ioReadShort()];
		ioReadBytes(buffer);
		prepareTag();
		return new String(buffer);
	}
	
	protected void prepareTag() throws IOException {
		prepareTag(false);
	}
	
	protected void prepareTag(boolean skip) throws IOException {
		ensureOpen();
		if (list != null && depth == list.depth) {
			resetName();
			if (list.payload > 0) {
				list.payload--;
				if (list.payload <= 0) removeList();
			}
			return;
		}
		type = TagType.getByID(ioReadByte());
		if (type == TagType.TAG_END) {
			resetName();
			depth--;
			if (list != null && depth == list.depth) type = TagType.LIST;
		} else if (!skip) {
			readName(name);
			hasName = true;
		} else {
			skipBytes(ioReadShort());
		}
		if (type == TagType.COMPOUND) 
			depth++;
		else if (type == TagType.LIST) 
			addList();
	}
	
	protected void resetName() {
		name.clear();
		hasName = false;
	}

	public void readNextEntry() throws IOException {
		readNextEntry(false);
	}
	
	public void readNextEntry(boolean skipPrepare) throws IOException {
		ensureOpen();
		if (list == null || list.depth != depth)
			if (type == TagType.COMPOUND || type == TagType.TAG_END)
				prepareTag(skipPrepare);
			else 
				throw new IOException("Next entry should only be called on COMPOUND or END: " + type.name());
		else if (list.type == TagType.COMPOUND) {
			depth++;
			prepareTag(skipPrepare);
		} else if (list.type == TagType.LIST) {
			addList();
		}
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
		list = new ListData(type, payload, ++depth, list);
	}

	private void removeList() {
		if (list == null) return;
		list = list.parent;
		depth--;
		if (list != null && depth == list.depth) type = TagType.LIST;
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
					this.list.payload--;
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
				while (this.list.payload > 0) {
					readNextEntry();
					list.addTag(readNBT());
					this.list.payload--;
				}
				removeList();
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
			return null;
		}
	}
	
	@Override
	public void skipTag() throws IOException {
		skipTag(false);
	}
	
	public void skipTag(boolean skipPrepare) throws IOException {
		ensureOpen();
		final boolean isList = list != null && depth == list.depth;
		switch (isList ? list.type : type) {
		case BYTE: skipTag(1, skipPrepare); break;
		case BYTE_ARRAY: skipTag(ioReadInt(), skipPrepare); break;
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
		case INT_ARRAY: skipTag(ioReadInt(), skipPrepare); break;
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
		case LONG_ARRAY: skipTag(ioReadInt(), skipPrepare);
		case SHORT: skipTag(2, skipPrepare); break;
		case STRING: skipTag(ioReadShort(), skipPrepare); break;
		case TAG_END: readNextEntry(skipPrepare); break;
		}
	}
	
	private void skipTag(int bytes, boolean skipPrepare) throws IOException {
		skipBytes(bytes);
		prepareTag(skipPrepare);
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
				if ((b & 0xC0) != 0x80) throw new UTFDataFormatException();
				buf.append((char) (((first & 0x1F) << 6) | (b & 0x3F)));
			} else if ((first & 0xF0) == 0xE0) {
				int b = ioReadByte();
				if ((b & 0xC0) != 0x80) throw new UTFDataFormatException();
				int c = ioReadByte();
				if ((c & 0xC0) != 0x80) throw new UTFDataFormatException();
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
