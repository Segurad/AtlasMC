package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.UUID;
import java.util.function.LongSupplier;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;

public abstract class AbstractNBTIOWriter implements NBTWriter {
	
	private int depth;
	private ListData list;
	private boolean closed;
	
	public AbstractNBTIOWriter() {
		depth = -1;
	}
	
	@Override
	public void writeEndTag() throws IOException {
		ensureOpen();
		if (depth == -1) 
			throw new IOException("No NBT to close available!");
		if (list != null && list.depth == depth && list.type != TagType.COMPOUND) 
			throw new IOException("Can not write EndTag to List");
		depth--;
		prepareTag(TagType.TAG_END, null);
	}
	
	@Override
	public void writeByteTag(CharSequence name, int value) throws IOException {
		prepareTag(TagType.BYTE, name);
		ioWriteByte(value);
	}
	
	@Override
	public void writeShortTag(CharSequence name, int value) throws IOException {
		prepareTag(TagType.SHORT, name);
		ioWriteShort(value);
	}
	
	@Override
	public void writeIntTag(CharSequence name, int value) throws IOException {
		prepareTag(TagType.INT, name);
		ioWriteInt(value);
	}
	
	@Override
	public void writeLongTag(CharSequence name, long value) throws IOException {
		prepareTag(TagType.LONG, name);
		ioWriteLong(value);
	}
	
	@Override
	public void writeFloatTag(CharSequence name, float value) throws IOException {
		prepareTag(TagType.FLOAT, name);
		ioWriteFloat(value);
	}
	
	@Override
	public void writeDoubleTag(CharSequence name, double value) throws IOException {
		prepareTag(TagType.DOUBLE, name);
		ioWriteDouble(value);
	}
	
	@Override
	public void writeByteArrayTag(CharSequence name, byte[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		prepareTag(TagType.BYTE_ARRAY, name);
		ioWriteInt(length);
		ioWriteBytes(data, offset, length);
	}

	@Override
	public void writeStringTag(CharSequence name, String value) throws IOException {
		if (value == null) 
			throw new IllegalArgumentException("Value can not be null!");
		prepareTag(TagType.STRING, name);
		byte[] buffer = value.getBytes();
		ioWriteShort(buffer.length);
		ioWriteBytes(buffer);
	}
	
	@Override
	public void writeListTag(CharSequence name, TagType payload, int payloadsize) throws IOException {
		if (payload == null)
			throw new IllegalArgumentException("PayloadType can not be null!");
		prepareTag(TagType.LIST, name);
		ioWriteByte(payload.getID());
		ioWriteInt(payloadsize);
		addList(payloadsize, payload);
	}
	
	@Override
	public void writeCompoundTag(CharSequence name) throws IOException {
		prepareTag(TagType.COMPOUND, name);
		depth++;		
	}
	
	@Override
	public void writeIntArrayTag(CharSequence name, int[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		prepareTag(TagType.INT_ARRAY, name);
		ioWriteInt(length);
		for (int i = 0, o = offset; i < length; i++, o++) {
			ioWriteInt(data[o]);
		}
	}
	
	@Override
	public void writeUUID(CharSequence name, UUID uuid) throws IOException {
		ensureOpen();
		if (uuid == null) 
			throw new IllegalArgumentException("UUID can not be null!");
		prepareTag(TagType.INT_ARRAY, name);
		ioWriteInt(4);
		ioWriteLong(uuid.getMostSignificantBits());
		ioWriteLong(uuid.getLeastSignificantBits());
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, long[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		prepareTag(TagType.LONG_ARRAY, name);
		ioWriteInt(length);
		for (int i = 0, o = offset; i < length; i++, o++) {
			ioWriteLong(data[o]);
		}
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, int length, LongSupplier supplier) throws IOException {
		if (supplier == null)
			throw new IllegalArgumentException("Supplier can not be null!");
		prepareTag(TagType.LONG_ARRAY, name);
		ioWriteInt(length);
		for (int i = 0; i < length; i++) {
			ioWriteLong(supplier.getAsLong());
		}
	}
	
	@Override
	public void writeNBT(NBT nbt) throws IOException {
		ensureOpen();
		if (nbt == null) 
			throw new IllegalArgumentException("NBT can not be null!");
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
	
	private void prepareTag(TagType type, CharSequence name) throws IOException {
		ensureOpen();
		if (list != null && list.depth == depth) {
			if (list.payload > 0) {
				if (type != list.type && !(list.type == TagType.COMPOUND && type == TagType.TAG_END))
					throw new IOException("TagType not campatiple with ListTag(" + list.type.name() + "):" + type.name());
				list.payload--;
				if (list.payload == 0) 
					removeList();
			} else 
				throw new IOException("Max Listpayload reached!");
		}
		ioWriteByte(type.getID());
		if (name == null) 
			return;
		writeUTF(name);
	}

	private void addList(int payload, TagType payloadType) {
		if (payload <= 0) return;
		list = new ListData(payloadType, payload, ++depth, list);
	}
	
	private void removeList() {
		if (list == null) return;
		list = list.parent;
		depth--;
	}
	
	public void close() {
		depth = Integer.MIN_VALUE;
		list = null;
	}
	
	protected final void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("Stream closed!");
	}
	
	/*
	 * --- Methods for writing data by subclass ---
	 */
	
	protected void writeUTF(CharSequence sequence) throws IOException {
		final int length = sequence.length();
		if (length == 0) return;
		int bytes = 0;
		for (int i = 0; i < length; i++) {
			char c = sequence.charAt(i);
			if (c >= 1 && c <= 0x7F) {
				bytes++;
			} else if (c == 0 || (c >= 0x80 && c <= 0x7FF)) {
				bytes+=2;
			} else if (c >= 0x800 && c <= 0xFFFF) {
				bytes+=3;
			}
		}
		if (bytes > 65535) throw new UTFDataFormatException();
		ioWriteShort(bytes);
		for (int i = 0; i < length; i++) {
			char c = sequence.charAt(i);
			if (c >= 1 && c <= 0x7F) {
				ioWriteByte(c);
			} else if (c == 0 || (c >= 0x80 && c <= 0x7FF)) {
				ioWriteByte(0xc0 | (0x1f & (c >> 6)));
				ioWriteByte(0x80 | (0x3f & c));
			} else if (c >= 0x800 && c <= 0xFFFF) {
				ioWriteByte(0xe0 | (0x0f & (c >> 12)));
				ioWriteByte(0x80 | (0x3f & (c >>  6)));
				ioWriteByte(0x80 | (0x3f & c));
			}
		}
	}
	
	protected abstract void ioWriteInt(int value) throws IOException;
	
	protected abstract void ioWriteByte(int value) throws IOException;
	
	protected abstract void ioWriteBytes(byte[] buffer) throws IOException;
	
	protected abstract void ioWriteShort(int value) throws IOException;
	
	protected abstract void ioWriteLong(long value) throws IOException;
	
	protected abstract void ioWriteFloat(float value) throws IOException;
	
	protected abstract void ioWriteDouble(double value) throws IOException;
	
	protected abstract void ioWriteBytes(byte[] buffer, int offset, int length) throws IOException;

}
