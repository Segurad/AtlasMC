package de.atlasmc.util.nbt.io;

import java.io.DataInput;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.CompoundTag;
import de.atlasmc.util.nbt.ListTag;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;

public class NBTIOReader implements NBTReader {
	
	private final DataInput in;
	private TagType type, listType;
	private String name;
	private int depth, highestList, restPayload, index;
	private int[] lists;
	private TagType[] listTypes;
	
	public NBTIOReader(DataInput in) {
		Validate.notNull(in, "DataInput can not be null!");
		this.in = in;
		highestList = -2;
		index = 0;
	}
	
	private void addList() throws IOException {
		if (lists == null) {
			lists = new int[8];
			listTypes = new TagType[4];
			Arrays.fill(lists, -1);
		}
		TagType type = TagType.getByID(in.readByte());
		int payload = in.readInt();
		if (payload > 0) {
			final int length = lists.length;
			if (index == length) {
				lists = Arrays.copyOf(lists, length*2);
				Arrays.fill(lists, length, length*2-1, -1);
			}
			lists[index-1] = restPayload;
			lists[index++] = ++depth;
			lists[index++] = payload;
			listTypes[index/2] = type;
			listType = type;
			restPayload = payload;
			highestList = depth;
		} else prepareTag();
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
		return listType;
	}
	
	@Override
	public int getRestPayload() {
		return restPayload;
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
		if (depth == highestList) {
			name = null;
			if (restPayload > 0) {
				if (--restPayload == 0) removeList();
			}
			return;
		}
		type = TagType.getByID(in.readByte());
		if (type == TagType.TAG_END) {
			name = null;
			depth--;
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
	public NBT readNBT() throws IOException {
		switch (depth == highestList ? listType : type) {
		case BYTE: return NBT.createByteTag(name, readByteTag());
		case BYTE_ARRAY: return NBT.createByteArrayTag(name, readByteArrayTag());
		case COMPOUND: {
			CompoundTag compound = new CompoundTag(name);
			final int depth = getDepth();
			if (depth != highestList) readNextEntry();
			while (depth >= getDepth()) {
				if (type == TagType.TAG_END) {
					readNextEntry();
					break;
				}
				compound.addTag(readNBT());
			}
		};
		case DOUBLE: return NBT.createDoubleTag(name, readDoubleTag());
		case FLOAT: return NBT.createFloatTag(name, readFloatTag());
		case INT: return NBT.createIntTag(name, readIntTag());
		case INT_ARRAY: return NBT.createIntArrayTag(name, readIntArrayTag());
		case LIST: 
			ListTag<NBT> list = new ListTag<NBT>(name, listType);
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
	public void readNextEntry() throws IOException {
		prepareTag();
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

	private void removeList() {
		if (lists == null) return;
		lists[--index] = -1;
		lists[--index] = -1;
		listTypes[index/2] = null;
		if (index >= 2) { 
			highestList = lists[index-2];
			restPayload = lists[index-1];
			listType = listTypes[(index-2)/2];
		} else {
			highestList = -1;
			restPayload = 0;
			listType = null;
		}
		depth--;
	}

	@Override
	public void skipNBT() throws IOException {
		switch (depth == highestList ? listType : type) {
		case BYTE: readByteTag(); break;
		case BYTE_ARRAY: readByteArrayTag(); break;
		case COMPOUND: {
			final int depth = getDepth();
			if (depth != highestList) readNextEntry();
			while (depth >= getDepth()) {
				if (type == TagType.TAG_END) {
					readNextEntry();
					break;
				}
				skipNBT();
			}
		};
		case DOUBLE: readDoubleTag(); break;
		case FLOAT: readFloatTag(); break;
		case INT: readIntTag(); break;
		case INT_ARRAY: readIntArrayTag(); break;
		case LIST: 
			name = null;
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
