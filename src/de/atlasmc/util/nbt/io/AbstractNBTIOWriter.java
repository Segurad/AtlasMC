package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.nbt.CompoundTag;
import de.atlasmc.util.nbt.ListTag;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.TagType;

public abstract class AbstractNBTIOWriter implements NBTWriter {
	
	private int depth;
	private ListData list;
	
	public AbstractNBTIOWriter() {
		depth = -1;
	}
	
	@Override
	public void writeEndTag() throws IOException {
		if (depth == -1) throw new IOException("No NBT to close available!");
		if (list == null || list.depth != depth || list.type != TagType.COMPOUND) depth--;
		prepareTag(0, null);
	}
	
	@Override
	public void writeByteTag(String name, int value) throws IOException {
		prepareTag(1, name);
		ioWriteByte(value);
	}
	
	@Override
	public void writeShortTag(String name, int value) throws IOException {
		prepareTag(2, name);
		ioWriteShort(value);
	}
	
	@Override
	public void writeIntTag(String name, int value) throws IOException {
		prepareTag(3, name);
		ioWriteInt(value);
	}
	
	@Override
	public void writeLongTag(String name, long value) throws IOException {
		prepareTag(4, name);
		ioWriteLong(value);
	}
	
	@Override
	public void writeFloatTag(String name, float value) throws IOException {
		prepareTag(5, name);
		ioWriteFloat(value);
	}
	
	@Override
	public void writeDoubleTag(String name, double value) throws IOException {
		prepareTag(6, name);
		ioWriteDouble(value);
	}
	
	@Override
	public void writeByteArrayTag(String name, byte[] data) throws IOException {
		prepareTag(7, name);
		ioWriteInt(data.length);
		ioWriteBytes(data);
	}
	
	@Override
	public void writeStringTag(String name, String value) throws IOException {
		if (value == null) throw new IllegalArgumentException("Value can not be null!");
		prepareTag(8, name);
		byte[] buffer = value.getBytes();
		ioWriteShort(buffer.length);
		ioWriteBytes(buffer);
	}
	
	@Override
	public void writeListTag(String name, TagType payload, int payloadsize) throws IOException {
		prepareTag(9, name);
		ioWriteByte(payload.ordinal());
		ioWriteInt(payloadsize);
		addList(payloadsize, payload);
	}
	
	@Override
	public void writeCompoundTag(String name) throws IOException {
		prepareTag(10, name);
		if (list == null || list.depth != depth || list.type != TagType.COMPOUND) depth++;
	}
	
	@Override
	public void writeIntArrayTag(String name, int[] data) throws IOException {
		if (data == null) throw new IllegalArgumentException("Data can not be null!");
		prepareTag(11, name);
		ioWriteInt(data.length);
		for (int i : data) {
			ioWriteInt(i);
		}
	}
	
	@Override
	public void writeUUID(String name, UUID uuid) throws IOException {
		if (uuid == null) throw new IllegalArgumentException("UUID can not be null!");
		writeIntArrayTag(name, new int[] {
				(int) (uuid.getMostSignificantBits()>>32),
				(int) uuid.getMostSignificantBits(),
				(int) (uuid.getLeastSignificantBits()>>32),
				(int) uuid.getLeastSignificantBits()
		});
	}
	
	@Override
	public void writeLongArrayTag(String name, long[] data) throws IOException {
		if (data == null) throw new IllegalArgumentException("Data can not be null!");
		prepareTag(12, name);
		ioWriteInt(data.length);
		for (long i : data) {
			ioWriteLong(i);
		}
	}
	
	@Override
	public void writeNBT(NBT nbt) throws IOException {
		if (nbt == null) throw new IllegalArgumentException("NBT can not be null!");
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
		if (list != null && list.depth == depth) {
			if (list.payload > 0) {
				if (list.type == TagType.COMPOUND) {
					list.payload--;
					if (type == 0 && list.payload == 0) removeList();
				} else if (type != list.type.ordinal()) {
					throw new IOException("TagType not campatiple with ListTag(" + list.type.name() + "):" + TagType.getByID(type));
				} else {
					list.payload--;
					if (list.payload == 0) removeList();
				}
			} else throw new IOException("Max Listpayload reached!");
			return;
		}
		if (name == null) return;
		ioWriteByte(type);
		byte[] buffer = name.getBytes();
		ioWriteShort(buffer.length);
		ioWriteBytes(buffer);
	}
	
	private void addList(int payload, TagType payloadType) {
		if (payload <= 0) return;
		list = new ListData(payloadType, payload, ++depth, list);
	}
	
	private void removeList() {
		if (list == null) return;
		list = list.last;
		depth--;
	}
	
	/*
	 * Methods for writing data by subclass
	 */
	
	protected abstract void ioWriteInt(int value) throws IOException;
	
	protected abstract void ioWriteByte(int value) throws IOException;
	
	protected abstract void ioWriteBytes(byte[] buffer) throws IOException;
	
	protected abstract void ioWriteShort(int value) throws IOException;
	
	protected abstract void ioWriteLong(long value) throws IOException;
	
	protected abstract void ioWriteFloat(float value) throws IOException;
	
	protected abstract void ioWriteDouble(double value) throws IOException;

}
