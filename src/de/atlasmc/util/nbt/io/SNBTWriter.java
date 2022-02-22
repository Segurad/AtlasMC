package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

import com.google.gson.stream.JsonWriter;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;

/**
 * {@link NBTWriter} implementation for NBT Json
 */
public class SNBTWriter extends JsonWriter implements NBTWriter {
	
	private int depth;
	private ListData list;
	private boolean closed;
	
	public SNBTWriter(Writer out) {
		super(out);
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
		endObject();
	}

	@Override
	public void writeByteTag(String name, int value) throws IOException {
		prepareTag(TagType.BYTE, name);
		value(value & 0xFF);
	}

	@Override
	public void writeShortTag(String name, int value) throws IOException {
		prepareTag(TagType.SHORT, name);
		value(value & 0xFFFF);
	}

	@Override
	public void writeIntTag(String name, int value) throws IOException {
		prepareTag(TagType.INT, name);
		value(value);
	}

	@Override
	public void writeLongTag(String name, long value) throws IOException {
		prepareTag(TagType.LONG, name);
		value(value);
	}

	@Override
	public void writeFloatTag(String name, float value) throws IOException {
		prepareTag(TagType.FLOAT, name);
		value(value);
	}

	@Override
	public void writeDoubleTag(String name, double value) throws IOException {
		prepareTag(TagType.DOUBLE, name);
		value(value);
	}

	@Override
	public void writeByteArrayTag(String name, byte[] data) throws IOException {
		prepareTag(TagType.BYTE_ARRAY, name);
		beginArray();
		for (byte b : data) {
			value(b);
		}
		endArray();
	}

	@Override
	public void writeStringTag(String name, String value) throws IOException {
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		prepareTag(TagType.STRING, name);
		value(value);
	}

	@Override
	public void writeListTag(String name, TagType payloadType, int payloadsize) throws IOException {
		if (payloadType == null)
			throw new IllegalArgumentException("PayloadType can not be null!");
		prepareTag(TagType.LIST, name);
		addList(payloadsize, payloadType);
	}

	@Override
	public void writeCompoundTag(String name) throws IOException {
		prepareTag(TagType.COMPOUND, name);
		beginObject();
	}

	@Override
	public void writeIntArrayTag(String name, int[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		prepareTag(TagType.INT_ARRAY, name);
		beginArray();
		for (int i : data) {
			value(i);
		}
		endArray();
	}

	@Override
	public void writeLongArrayTag(String name, long[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		prepareTag(TagType.LONG_ARRAY, name);
		beginArray();
		for (long l : data) {
			value(l);
		}
		endArray();
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

	@Override
	public void writeUUID(String name, UUID uuid) throws IOException {
		ensureOpen();
		if (uuid == null) 
			throw new IllegalArgumentException("UUID can not be null!");
		writeIntArrayTag(name, new int[] {
				(int) (uuid.getMostSignificantBits()>>32),
				(int) uuid.getMostSignificantBits(),
				(int) (uuid.getLeastSignificantBits()>>32),
				(int) uuid.getLeastSignificantBits()
		});
	}
	
	private void prepareTag(TagType type, String name) throws IOException {
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
		if (name == null) 
			return;
		name(name);
	}
	
	private void addList(int payload, TagType payloadType) throws IOException {
		if (payload <= 0) return;
		list = new ListData(payloadType, payload, ++depth, list);
		beginArray();
	}
	
	private void removeList() throws IOException {
		if (list == null) return;
		list = list.parent;
		depth--;
		endArray();
	}

	protected final void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("Stream closed!");
	}
	
	@Override
	public void close() throws IOException {
		super.close();
		closed = true;
		list = null;
	}

}
