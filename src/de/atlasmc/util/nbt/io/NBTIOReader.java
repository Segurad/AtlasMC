package de.atlasmc.util.nbt.io;

import java.io.DataInput;
import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.nbt.CompoundTag;
import de.atlasmc.util.nbt.ListTag;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;

public class NBTIOReader implements NBTReader {
	
	private final DataInput in;
	private TagType type;
	private String name;
	private int depth;
	private ListData list;
	
	public NBTIOReader(DataInput in) {
		if (in == null) throw new IllegalArgumentException("DataInput can not be null!");
		this.in = in;
		getType();
	}
	
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
		if (type == null)
			try {
				prepareTag();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return type;
	}
	
	private void prepareTag() throws IOException {
		if (list != null && depth == list.depth) {
			name = null;
			if (list.payload > 0) {
				list.payload--;
				if (list.payload <= 0) removeList();
			}
			return;
		}
		type = TagType.getByID(in.readByte());
		if (type == TagType.TAG_END) {
			name = null;
			depth--;
			if (list != null && depth == list.depth) type = TagType.LIST;
		} else {
			byte[] buffer = new byte[in.readShort()];
			in.readFully(buffer);
			name = new String(buffer);
			if (type == TagType.COMPOUND) depth++;
			if (type == TagType.LIST) addList();
		}
	}
	
	@Override
	public byte[] readByteArrayTag() throws IOException {
		byte[] data = new byte[in.readInt()];
		in.readFully(data);
		prepareTag();
		return data;
	}
	
	@Override
	public byte readByteTag() throws IOException {
		byte data = in.readByte();
		prepareTag();
		return data;
	}
	
	@Override
	public double readDoubleTag() throws IOException {
		double data = in.readDouble();
		prepareTag();
		return data;
	}
	
	@Override
	public float readFloatTag() throws IOException {
		float data = in.readFloat();
		prepareTag();
		return data;
	}
	
	@Override
	public int[] readIntArrayTag() throws IOException {
		int[] data = new int[in.readInt()];
		for (int i = 0; i < data.length; i++) {
			data[i] = in.readInt();
		}
		prepareTag();
		return data;
	}
	
	@Override
	public int readIntTag() throws IOException {
		int data = in.readInt();
		prepareTag();
		return data;
	}
	
	@Override
	public long[] readLongArrayTag() throws IOException {
		long[] data = new long[in.readInt()];
		for (int i = 0; i < data.length; i++) {
			data[i] = in.readLong();
		}
		prepareTag();
		return data;
	}
	
	@Override
	public long readLongTag() throws IOException {
		long data = in.readLong();
		prepareTag();
		return data;
	}
	
	@Override
	public void readNextEntry() throws IOException {
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
	public short readShortTag() throws IOException {
		short data = in.readShort();
		prepareTag();
		return data;
	}
	
	@Override
	public String readStringTag() throws IOException {
		byte[] buffer = new byte[in.readShort()];
		in.readFully(buffer);
		prepareTag();
		return new String(buffer);
	}

	@Override
	public UUID readUUID() throws IOException {
		int[] data = readIntArrayTag();
		if (data.length != 4) throw new NBTException("Invalid UUID data length: " + data.length);
		return new UUID((data[0]<<32)+data[1], (data[2]<<32)+data[3]);
	}
	
	private void addList() throws IOException {
		TagType type = TagType.getByID(in.readByte());
		int payload = in.readInt();
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
		final boolean isList = list != null && depth == list.depth;
		switch (isList ? list.type : type) {
		case BYTE: return NBT.createByteTag(name, readByteTag());
		case BYTE_ARRAY: return NBT.createByteArrayTag(name, readByteArrayTag());
		case COMPOUND: {
			if (isList) {
				final ListTag<CompoundTag> list = new ListTag<CompoundTag>(getFieldName(), this.list.type);
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
			final ListTag<NBT> list = new ListTag<NBT>(name, this.list.type);
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
	public void skipNBT() throws IOException {
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
						skipNBT();
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
				skipNBT();
			}
		case DOUBLE: readDoubleTag(); break;
		case FLOAT: readFloatTag(); break;
		case INT: readIntTag(); break;
		case INT_ARRAY: readIntArrayTag(); break;
		case LIST: 
			if (isList) {
				while (list.payload > 0) {
					readNextEntry();
					skipNBT();
					list.payload--;
				}
				removeList();
				return;
			}
			while (getRestPayload() > 0) {
				skipNBT();
			}
			break;
		case LONG: readLongTag(); break;
		case LONG_ARRAY: readLongArrayTag();
		case SHORT: readShortTag(); break;
		case STRING: readStringTag(); break;
		case TAG_END: readNextEntry(); break;
		}
	}

}
