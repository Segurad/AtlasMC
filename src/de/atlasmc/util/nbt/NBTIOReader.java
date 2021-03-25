package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.IOException;
import java.util.Arrays;

import de.atlasmc.util.Validate;

public class NBTIOReader implements NBTReader {
	
	private final DataInput in;
	private TagType type, listType;
	private String name;
	private int depth, highestList, restPayload;
	private int[] lists;
	private TagType[] listTypes;
	
	public NBTIOReader(DataInput in) {
		Validate.notNull(in, "DataInput can not be null!");
		this.in = in;
		depth = -1;
		highestList = -2;
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
	
	@Override
	public TagType getListType() {
		return listType;
	}
	
	@Override
	public int getRestPayload() {
		return restPayload;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public byte readByteTag() throws IOException {
		return in.readByte();
	}
	
	@Override
	public short readShortTag() throws IOException {
		return in.readShort();
	}
	
	@Override
	public int readIntTag() throws IOException {
		return in.readInt();
	}
	
	@Override
	public long readLongTag() throws IOException {
		return in.readLong();
	}
	
	@Override
	public float readFloatTag() throws IOException {
		return in.readFloat();
	}
	
	@Override
	public double readDoubleTag() throws IOException {
		return in.readDouble();
	}
	
	@Override
	public byte[] readByteArrayTag() throws IOException {
		byte[] data = new byte[in.readInt()];
		in.readFully(data);
		return data;
	}
	
	@Override
	public String readStringTag() throws IOException {
		byte[] buffer = new byte[in.readShort()];
		in.readFully(buffer);
		return new String(buffer);
	}
	
	@Override
	public void readListTag() throws IOException {
		addList();
	}
	
	@Override
	public int[] readIntArrayTag() throws IOException {
		int[] data = new int[in.readInt()];
		for (int i = 0; i < data.length; i++) {
			data[i] = in.readInt();
		}
		return data;
	}
	
	@Override
	public long[] readLongArrayTag() throws IOException {
		long[] data = new long[in.readInt()];
		for (int i = 0; i < data.length; i++) {
			data[i] = in.readLong();
		}
		return data;
	}
	
	@Override
	public Object readTag() throws IOException {
		TagType swtype = (type == TagType.LIST) ? listType : type;
		switch (swtype) {
		case BYTE: return readByteTag();
		case BYTE_ARRAY: return readByteArrayTag();
		case DOUBLE: return readDoubleTag();
		case FLOAT: return readFloatTag();
		case INT: return readIntTag();
		case INT_ARRAY: return readIntArrayTag();
		case LONG: return readLongTag();
		case LONG_ARRAY: return readLongArrayTag();
		case SHORT: return readShortTag();
		case STRING: return readStringTag();
		default: return null;		
		}
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
		} else {
			byte[] buffer = new byte[in.readShort()];
			in.readFully(buffer);
			name = new String(buffer);
		}
	}
	
	private void addList() throws IOException {
		if (lists == null) {
			lists = new int[10];
			listTypes = new TagType[5];
			Arrays.fill(lists, -1);
		}
		for (int i = 0; i < lists.length; i+=2) {
			if (lists[i] == -1) {
				lists[i] = ++depth;
				listTypes[i] = TagType.getByID(in.readByte());
				lists[i++] = in.readInt();
				highestList = depth;
				break;
			}
		}
	}
	
	private void removeList() {
		if (lists == null) return;
		int nextHighest = -1;
		int restPayload = -1;
		TagType listType = null;
		for (int i = 0; i < lists.length; i+=2) {
			int val = lists[i];
			if (val == highestList) {
				lists[i] = -1;
				lists[i+1] = -1;
				listTypes = null;
			} else if (val > nextHighest) {
				nextHighest = val;
				restPayload = lists[i+1];
				listType = listTypes[i];
			}
		}
		highestList = nextHighest == -1 ? -1 : nextHighest;
		this.restPayload = restPayload;
		this.listType = listType;
	}

}
