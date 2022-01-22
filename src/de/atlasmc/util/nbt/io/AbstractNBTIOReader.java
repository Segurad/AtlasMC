package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.nbt.CompoundTag;
import de.atlasmc.util.nbt.ListTag;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;

public abstract class AbstractNBTIOReader implements NBTReader {
	
	private TagType type, markType;
	private String name, markName;
	private int depth, markDepth;
	private ListData list, markList;
	private boolean closed;
	
	@Override
	public int getDepth() {
		return depth;
	}
	
	@Override
	public String getFieldName() {
		if (type == null) getType();
		return name;
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
		ensureOpen();
		if (list != null && depth == list.depth) {
			name = null;
			if (list.payload > 0) {
				list.payload--;
				if (list.payload <= 0) removeList();
			}
			return;
		}
		type = TagType.getByID(ioReadByte());
		if (type == TagType.TAG_END) {
			name = null;
			depth--;
			if (list != null && depth == list.depth) type = TagType.LIST;
		} else {
			byte[] buffer = new byte[ioReadShort()];
			ioReadBytes(buffer);
			name = new String(buffer);
			if (type == TagType.COMPOUND) depth++;
			if (type == TagType.LIST) addList();
		}
	}
	
	@Override
	public void readNextEntry() throws IOException {
		ensureOpen();
		if (list == null || list.depth != depth)
			if (type == TagType.COMPOUND || type == TagType.TAG_END)
				prepareTag();
			else 
				throw new IOException("Next entry should only be called on COMPOUND or END: " + type.name());
		else if (list.type == TagType.COMPOUND) {
			depth++;
			prepareTag();
		} else if (list.type == TagType.LIST) {
			addList();
		}
	}
	
	@Override
	public UUID readUUID() throws IOException {
		ensureOpen();
		int[] data = readIntArrayTag();
		if (data.length != 4) throw new NBTException("Invalid UUID data length: " + data.length);
		return new UUID((data[0]<<32)+data[1], (data[2]<<32)+data[3]);
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
		list = list.last;
		depth--;
		if (list != null && depth == list.depth) type = TagType.LIST;
	}
	
	@Override
	public NBT readNBT() throws IOException {
		ensureOpen();
		final boolean isList = list != null && depth == list.depth;
		switch (isList ? list.type : type) {
		case BYTE: return NBT.createByteTag(name, readByteTag());
		case BYTE_ARRAY: return NBT.createByteArrayTag(name, readByteArrayTag());
		case COMPOUND: {
			if (isList) {
				final ListTag<CompoundTag> list = new ListTag<>(getFieldName(), this.list.type);
				readNextEntry(); // move out of list header
				while (this.list.payload > 0) {
					CompoundTag compound = new CompoundTag(name);
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
			final CompoundTag compound = new CompoundTag(getFieldName());
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
		case DOUBLE: return NBT.createDoubleTag(name, readDoubleTag());
		case FLOAT: return NBT.createFloatTag(name, readFloatTag());
		case INT: return NBT.createIntTag(name, readIntTag());
		case INT_ARRAY: return NBT.createIntArrayTag(name, readIntArrayTag());
		case LIST: 
			if (isList) {
				ListTag<NBT> list = new ListTag<>(getFieldName(), TagType.LIST);
				while (this.list.payload > 0) {
					readNextEntry();
					list.addTag(readNBT());
					this.list.payload--;
				}
				removeList();
				return list;
			}
			final ListTag<NBT> list = new ListTag<>(name, this.list.type);
			name = null;
			while (getRestPayload() > 0) {
				list.addTag(readNBT());
			}
			return list;
		case LONG: return NBT.createLongTag(name, readLongTag());
		case LONG_ARRAY: return NBT.createLongArrayTag(name, readLongArrayTag());
		case SHORT: return NBT.createShortTag(name, readShortTag());
		case STRING: return NBT.createStringTag(name, readStringTag());
		case TAG_END: readNextEntry(); return null;
		default:
			return null;
		}
	}
	
	@Override
	public void skipTag() throws IOException {
		ensureOpen();
		final boolean isList = list != null && depth == list.depth;
		switch (isList ? list.type : type) {
		case BYTE: readByteTag(); break;
		case BYTE_ARRAY: readByteArrayTag(); break;
		case COMPOUND: 
			if (isList) {
				readNextEntry(); // move out of list header
				while (list.payload > 0) {
					final int depth = getDepth(); // root depth of compound
					while (depth <= getDepth()) {
						if (type == TagType.TAG_END) {
							readNextEntry(); // move out of list or to next compound in list
							break;
						}
						skipTag();
					}
					list.payload--;
				}
				removeList();
				return;
			}
			readNextEntry(); // move to first compound entry
			final int depth = getDepth(); // root depth of compound
			while (depth <= getDepth()) {
				if (type == TagType.TAG_END) {
					readNextEntry(); // skip end
					break;
				}
				skipTag();
			}
		case DOUBLE: readDoubleTag(); break;
		case FLOAT: readFloatTag(); break;
		case INT: readIntTag(); break;
		case INT_ARRAY: readIntArrayTag(); break;
		case LIST: 
			if (isList) {
				while (list.payload > 0) {
					readNextEntry();
					skipTag();
					list.payload--;
				}
				removeList();
				return;
			}
			while (getRestPayload() > 0) {
				skipTag();
			}
			break;
		case LONG: readLongTag(); break;
		case LONG_ARRAY: readLongArrayTag();
		case SHORT: readShortTag(); break;
		case STRING: readStringTag(); break;
		case TAG_END: readNextEntry(); break;
		}
	}
	
	@Override
	public void mark() {
		markDepth = depth;
		markList = list;
		markName = name;
		markType = type;
		ioMark();
	}

	@Override
	public void reset() {
		depth = markDepth;
		list = markList;
		name = markName;
		type = markType;
		ioReset();
	}

	@Override
	public void search(String key, TagType stype, boolean slist) throws IOException {
		ensureOpen();
		final int depth = getDepth();
		while (depth <= getDepth()) {
			// check if current tag is the result
			if ((key == null || key.equals(name)) && 
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

}
