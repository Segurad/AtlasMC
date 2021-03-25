package de.atlasmc.util.nbt;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import de.atlasmc.util.Validate;

public class NBTIOWriter implements NBTWriter {
	
	private final DataOutput out;
	private int depth, highestList, restPayload;
	private int[] lists;
	
	public NBTIOWriter(DataOutput out) {
		Validate.notNull(out, "DataOutput can not be null!");
		this.out = out;
		depth = -1;
		highestList = -2;
	}
	
	@Override
	public void writeEndTag() throws IOException {
		if (depth == -1) throw new IOException("No NBT to close available!");
		out.write(0);
		depth--;
	}
	
	@Override
	public void writeByteTag(String name, int value) throws IOException {
		prepareTag(0, name);
		out.write(value);
	}
	
	@Override
	public void writeShortTag(String name, int value) throws IOException {
		prepareTag(1, name);
		out.writeShort(value);
	}
	
	@Override
	public void writeIntTag(String name, int value) throws IOException {
		prepareTag(2, name);
		out.writeInt(value);
	}
	
	@Override
	public void writeLongTag(String name, long value) throws IOException {
		prepareTag(4, name);
		out.writeLong(value);
	}
	
	@Override
	public void writeFloatTag(String name, float value) throws IOException {
		prepareTag(5, name);
		out.writeFloat(value);
	}
	
	@Override
	public void writeDoubleTag(String name, double value) throws IOException {
		prepareTag(6, name);
		out.writeDouble(value);
	}
	
	@Override
	public void writeByteArrayTag(String name, byte[] data) throws IOException {
		prepareTag(7, name);
		out.writeInt(data.length);
		out.write(data);
	}
	
	@Override
	public void writeStringTag(String name, String value) throws IOException {
		Validate.notNull(value, "Value can not be null!");
		prepareTag(8, name);
		byte[] buffer = value.getBytes();
		out.writeShort(buffer.length);
		out.write(buffer);
	}
	
	@Override
	public void writeListTag(String name, TagType payload, int payloadsize) throws IOException {
		prepareTag(9, name);
		out.write(payload.ordinal());
		out.writeInt(payloadsize);
		addList(payloadsize);
	}
	
	@Override
	public void writeCompoundTag(String name) throws IOException {
		prepareTag(10, name);
		depth++;
	}
	
	@Override
	public void writeIntArrayTag(String name, int[] data) throws IOException {
		Validate.notNull(data, "Data can not be null!");
		prepareTag(11, name);
		out.writeInt(data.length);
		for (int i : data) {
			out.writeInt(i);
		}
	}
	
	@Override
	public void writeLongArrayTag(String name, long[] data) throws IOException {
		Validate.notNull(data, "Data can not be null!");
		prepareTag(12, name);
		out.writeInt(data.length);
		for (long i : data) {
			out.writeLong(i);
		}
	}
	
	@Override
	public void writeNBT(NBT nbt) throws IOException {
		Validate.notNull(nbt, "NBT can not be null!");
		TagType type = nbt.getType();
		switch (type) {
		case BYTE: 
			writeByteTag(nbt.getName(), (int) nbt.getData());
			break;
		case BYTE_ARRAY:
			writeByteArrayTag(nbt.getName(), (byte[]) nbt.getData());
			break;
		case COMPOUND:
			CompoundTag tag = (CompoundTag) nbt;
			writeCompoundTag(tag.getName());
			for (NBT entry : tag) {
				writeNBT(entry);
			}
			writeEndTag();
			break;
		case DOUBLE:
			writeDoubleTag(nbt.getName(), (double) nbt.getData());
			break;
		case FLOAT:
			writeFloatTag(nbt.getName(), (float) nbt.getData());
			break;
		case INT:
			writeFloatTag(nbt.getName(), (float) nbt.getData());
			break;
		case INT_ARRAY:
			writeIntArrayTag(nbt.getName(), (int[]) nbt.getData());
			break;
		case LIST:
			@SuppressWarnings("unchecked") ListTag<NBT> listTag = (ListTag<NBT>) nbt;
			writeListTag(listTag.getName(), listTag.getDataType(), listTag.size());
			for (NBT entry : listTag) {
				writeNBT(entry);
			}
			break;
		case LONG:
			writeLongTag(nbt.getName(), (long) nbt.getData());
			break;
		case LONG_ARRAY:
			writeLongArrayTag(nbt.getName(), (long[]) nbt.getData());
			break;
		case SHORT:
			writeShortTag(nbt.getName(), (int) nbt.getData());
			break;
		case STRING:
			writeStringTag(nbt.getName(), (String) nbt.getData());
			break;
		default:
			break;
		}
	}
	
	private void prepareTag(int type, String name) throws IOException {
		if (highestList == depth) {
			if (restPayload > 0) {
				if (--restPayload == 0) removeList();
			} else throw new IOException("Max Listpayload reached!");
			return;
		}
		if (name == null) return;
		out.write(type);
		byte[] buffer = name.getBytes();
		out.writeShort(buffer.length);
		out.write(buffer);
	}
	
	private void addList(int payload) {
		if (lists == null) {
			lists = new int[10];
			Arrays.fill(lists, -1);
		}
		for (int i = 0; i < lists.length; i+=2) {
			if (lists[i] == -1) {
				lists[i] = ++depth;
				lists[i++] = payload;
				highestList = depth;
				break;
			}
		}
	}
	
	private void removeList() {
		if (lists == null) return;
		int nextHighest = -1;
		int restPayload = -1;
		for (int i = 0; i < lists.length; i+=2) {
			int val = lists[i];
			if (val == highestList) {
				lists[i] = -1;
				lists[i+1] = -1;
			} else if (val > nextHighest) {
				nextHighest = val;
				restPayload = lists[i+1];
			}
		}
		highestList = nextHighest == -1 ? -1 : nextHighest;
		this.restPayload = restPayload;
	}

}