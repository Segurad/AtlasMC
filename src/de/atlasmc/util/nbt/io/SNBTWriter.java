package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.Writer;
import java.util.UUID;
import java.util.function.LongSupplier;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;

/**
 * {@link NBTWriter} implementation for SNBT
 */
public class SNBTWriter implements NBTWriter {
	
	private int depth;
	private ListData list;
	private boolean closed;
	private Writer out;
	private boolean separator;
	private boolean removeList;
	
	public SNBTWriter(Writer out) {
		this.out = out;
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
		out.write('}');
		removeList();
	}

	@Override
	public void writeByteTag(CharSequence name, int value) throws IOException {
		prepareTag(TagType.BYTE, name);
		out.write(Byte.toString((byte) value));
		out.write('B');
		removeList();
	}

	@Override
	public void writeShortTag(CharSequence name, int value) throws IOException {
		prepareTag(TagType.SHORT, name);
		out.write(Short.toString((short) value));
		out.write('S');
		removeList();
	}

	@Override
	public void writeIntTag(CharSequence name, int value) throws IOException {
		prepareTag(TagType.INT, name);
		out.write(Integer.toString(value));
		removeList();
	}

	@Override
	public void writeLongTag(CharSequence name, long value) throws IOException {
		prepareTag(TagType.LONG, name);
		out.write(Long.toString(value));
		out.write('L');
		removeList();
	}

	@Override
	public void writeFloatTag(CharSequence name, float value) throws IOException {
		prepareTag(TagType.FLOAT, name);
		out.write(Float.toString(value));
		out.write('F');
		removeList();
	}

	@Override
	public void writeDoubleTag(CharSequence name, double value) throws IOException {
		prepareTag(TagType.DOUBLE, name);
		out.write(Double.toString(value));
		out.write('D');
		removeList();
	}
	
	@Override
	public void writeByteArrayTag(CharSequence name, byte[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		prepareTag(TagType.BYTE_ARRAY, name);
		out.write('[');
		out.write('B');
		out.write(';');
		boolean separator = false;
		for (int i = 0, o = offset; i < length; i++, o++) {
			if (separator)
				out.write(',');
			else
				separator = true;
			out.write(Byte.toString(data[o]));
		}
		out.write(']');
		removeList();
	}

	@Override
	public void writeStringTag(CharSequence name, String value) throws IOException {
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		prepareTag(TagType.STRING, name);
		out.write('"');
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '"':
				out.write('\\');
				out.write('"');
				break;
			case '\\':
				out.write('\\');
				out.write('\\');
				break;
			case '\t':
				out.write('\\');
				out.write('t');
				break;
			case '\b':
				out.write('\\');
				out.write('b');
				break;
			case '\n':
				out.write('\\');
				out.write('n');
				break;
			case '\r':
				out.write('\\');
				out.write('r');
				break;
			case '\f':
				out.write('\\');
				out.write('f');
				break;
			default:
				out.write(c);
				break;
			}
		}
		out.write('"');
		removeList();
	}

	@Override
	public void writeListTag(CharSequence name, TagType payloadType, int payloadsize) throws IOException {
		if (payloadType == null)
			throw new IllegalArgumentException("PayloadType can not be null!");
		prepareTag(TagType.LIST, name);
		addList(payloadsize, payloadType);
	}

	@Override
	public void writeCompoundTag(CharSequence name) throws IOException {
		prepareTag(TagType.COMPOUND, name);
		separator = false;
		out.write('{');
	}

	@Override
	public void writeIntArrayTag(CharSequence name, int[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		prepareTag(TagType.INT_ARRAY, name);
		out.write('[');
		out.write('I');
		out.write(';');
		boolean separator = false;
		for (int i = 0, o = offset; i < length; i++, o++) {
			if (separator)
				out.write(',');
			else
				separator = true;
			out.write(Integer.toString(data[o]));
		}
		out.write(']');
		removeList();
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, long[] data, int offset, int length) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		prepareTag(TagType.LONG_ARRAY, name);
		out.write('[');
		out.write('L');
		out.write(';');
		boolean separator = false;
		for (int i = 0, o = offset; i < length; i++, o++) {
			if (separator)
				out.write(',');
			else
				separator = true;
			out.write(Long.toString(data[o]));
		}
		out.write(']');
		removeList();
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, int length, LongSupplier supplier) throws IOException {
		if (supplier == null)
			throw new IllegalArgumentException("Supplier can not be null!");
		prepareTag(TagType.LONG_ARRAY, name);
		out.write('[');
		out.write('L');
		out.write(';');
		boolean separator = false;
		for (int i = 0; i < length; i++) {
			if (separator)
				out.write(',');
			else
				separator = true;
			out.write(Long.toString(supplier.getAsLong()));
		}
		out.write(']');
		removeList();
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
	public void writeUUID(CharSequence name, UUID uuid) throws IOException {
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
	
	private void prepareTag(TagType type, CharSequence name) throws IOException {
		ensureOpen();
		if (list != null && list.depth == depth) {
			if (list.payload > 0) {
				if (type != list.type && !(list.type == TagType.COMPOUND && type == TagType.TAG_END))
					throw new IOException("TagType not campatiple with ListTag(" + list.type.name() + "):" + type.name());
				list.payload--;
				if (list.payload == 0) 
					removeList = true;
			} else 
				throw new IOException("Max Listpayload reached!");
		}
		if (separator)
			out.write(',');
		else
			separator = true;
		if (name == null) 
			return;
		out.write('"');
		final int length = name.length();
		for (int i = 0; i < length; i++)
			out.write(name.charAt(i));
		out.write('"');
		out.write(':');
	}
	
	private void addList(int payload, TagType payloadType) throws IOException {
		if (payload <= 0) return;
		list = new ListData(payloadType, payload, ++depth, list);
		separator = false;
		out.write('[');
	}
	
	private void removeList() throws IOException {
		if (!removeList)
			return;
		removeList = false;
		if (list == null) 
			return;
		if (list.depth != depth)
			removeList = true;
		list = list.parent;
		depth--;
		out.write(']');
	}

	protected final void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("Stream closed!");
	}
	
	@Override
	public void close() throws IOException {
		out.close();
		out = null;
		closed = true;
		list = null;
	}

}
