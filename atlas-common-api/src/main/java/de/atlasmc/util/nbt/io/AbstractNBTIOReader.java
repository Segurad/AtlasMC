package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.UUID;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import de.atlasmc.util.map.key.CharKeyBuffer;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;

public abstract class AbstractNBTIOReader extends AbstractNBTReader {

	private TagType type;
	private TagType markType;
	private CharKeyBuffer name;
	private boolean prepared;
	private boolean markPrepared;
	private boolean hasName;
	private String markName;
	private int depth = -1;
	private int markDepth;
	private ListData list;
	private ListData markList;
	private int arrayTagPayload = -1;
	private int markArrayTagPayload = -1;
	private final boolean unnamedRoot;
	private byte[] nameBuf = null;

	public AbstractNBTIOReader(boolean unnamedRoot) {
		name = new CharKeyBuffer();
		this.unnamedRoot = unnamedRoot;
	}

	@Override
	public int getDepth() throws IOException {
		if (depth < 0)
			return depth;
		prepareTag();
		return depth;
	}

	@Override
	public CharSequence getFieldName() throws IOException {
		prepareTag();
		return hasName ? name.getView() : null;
	}

	@Override
	public TagType getListType() throws IOException {
		prepareTag();
		return list == null ? null : list.type;
	}

	@Override
	public int getRestPayload() throws IOException {
		prepareTag();
		if (list == null || list.depth != depth)
			return 0;
		return list.payload;
	}

	@Override
	public int getNextPayload() throws IOException {
		prepareTag();
		if (list == null || list.depth >= depth)
			return 0;
		return list.depth;
	}

	@Override
	public TagType getType() throws IOException {
		prepareTag();
		return type;
	}

	/**
	 * Call to mark the current tag as consumed. In most cases this can be called
	 * immediately.
	 */
	private void tagConsumed() {
		prepared = false;
		resetName();
		arrayTagPayload = -1;
		if (list != null && (depth == list.depth || (list.type == TagType.COMPOUND && list.depth + 1 == depth))) {
			if (list.payload > 0) {
				list.payload--;
				if (list.payload <= 0)
					removeList();
			}
			return;
		}
	}

	@Override
	public void readByteArrayTag(IntConsumer dataConsumer) throws IOException {
		prepareTag();
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		final int payload = arrayTagPayload;
		for (int i = 0; i < payload; i++)
			dataConsumer.accept(ioReadByte());
		tagConsumed();
	}

	@Override
	public int readByteArrayTag(byte[] buf) throws IOException {
		prepareTag();
		int bytesRead = 0;
		for (int i = 0; i < buf.length; i++) {
			if (arrayTagPayload <= 0)
				break;
			bytesRead++;
			arrayTagPayload--;
			buf[i] = ioReadByte();
		}
		if (arrayTagPayload <= 0)
			tagConsumed();
		return bytesRead;
	}

	@Override
	public byte[] readByteArrayTag() throws IOException {
		prepareTag();
		byte[] data = new byte[arrayTagPayload];
		ioReadBytes(data);
		tagConsumed();
		return data;
	}

	@Override
	public byte readByteTag() throws IOException {
		prepareTag();
		byte data = 0;
		if (type == TagType.BYTE) {
			data = ioReadByte();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case SHORT:
				data = (byte) ioReadShort();
				break;
			case LONG:
				data = (byte) ioReadLong();
				break;
			case FLOAT:
				data = (byte) ioReadFloat();
				break;
			case DOUBLE:
				data = (byte) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public double readDoubleTag() throws IOException {
		prepareTag();
		double data = 0;
		if (type == TagType.DOUBLE) {
			data = ioReadDouble();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadDouble();
				break;
			case SHORT:
				data = ioReadShort();
				break;
			case INT:
				data = ioReadInt();
				break;
			case LONG:
				data = ioReadLong();
				break;
			case FLOAT:
				data = ioReadFloat();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public float readFloatTag() throws IOException {
		prepareTag();
		float data = 0;
		if (type == TagType.FLOAT) {
			data = ioReadFloat();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case SHORT:
				data = ioReadShort();
				break;
			case INT:
				data = ioReadInt();
				break;
			case LONG:
				data = ioReadLong();
				break;
			case DOUBLE:
				data = (float) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public void readIntArrayTag(IntConsumer dataConsumer) throws IOException {
		prepareTag();
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		final int payload = arrayTagPayload;
		for (int i = 0; i < payload; i++)
			dataConsumer.accept(ioReadInt());
		tagConsumed();
	}

	@Override
	public int readIntArrayTag(int[] buf) throws IOException {
		prepareTag();
		int intsRead = 0;
		for (int i = 0; i < buf.length; i++) {
			if (arrayTagPayload <= 0)
				break;
			intsRead++;
			arrayTagPayload--;
			buf[i] = ioReadInt();
		}
		if (arrayTagPayload <= 0)
			tagConsumed();
		return intsRead;
	}

	@Override
	public int[] readIntArrayTag() throws IOException {
		prepareTag();
		int[] data = new int[arrayTagPayload];
		for (int i = 0; i < data.length; i++) {
			data[i] = ioReadInt();
		}
		tagConsumed();
		return data;
	}

	@Override
	public int readIntTag() throws IOException {
		prepareTag();
		int data = 0;
		if (type == TagType.INT) {
			data = ioReadInt();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case SHORT:
				data = ioReadShort();
				break;
			case LONG:
				data = (int) ioReadLong();
				break;
			case FLOAT:
				data = (int) ioReadFloat();
				break;
			case DOUBLE:
				data = (int) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public void readLongArrayTag(LongConsumer dataConsumer) throws IOException {
		prepareTag();
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		final int payload = arrayTagPayload;
		for (int i = 0; i < payload; i++)
			dataConsumer.accept(ioReadLong());
		tagConsumed();
	}

	@Override
	public int readLongArrayTag(long[] buf) throws IOException {
		prepareTag();
		int longsRead = 0;
		for (int i = 0; i < buf.length; i++) {
			if (arrayTagPayload <= 0)
				break;
			longsRead++;
			arrayTagPayload--;
			buf[i] = ioReadLong();
		}
		if (arrayTagPayload <= 0)
			tagConsumed();
		return longsRead;
	}

	@Override
	public long[] readLongArrayTag() throws IOException {
		prepareTag();
		long[] data = new long[arrayTagPayload];
		for (int i = 0; i < data.length; i++) {
			data[i] = ioReadLong();
		}
		tagConsumed();
		return data;
	}

	@Override
	public long readLongTag() throws IOException {
		prepareTag();
		long data = 0;
		if (type == TagType.LONG) {
			data = ioReadLong();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case SHORT:
				data = ioReadShort();
				break;
			case INT:
				data = ioReadInt();
				break;
			case FLOAT:
				data = (long) ioReadFloat();
				break;
			case DOUBLE:
				data = (long) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public short readShortTag() throws IOException {
		prepareTag();
		short data = 0;
		if (type == TagType.SHORT) {
			data = ioReadShort();
		} else { // misc number read
			switch (type) {
			case BYTE:
				data = ioReadByte();
				break;
			case INT:
				data = (short) ioReadInt();
				break;
			case LONG:
				data = (short) ioReadLong();
				break;
			case FLOAT:
				data = (short) ioReadFloat();
				break;
			case DOUBLE:
				data = (short) ioReadDouble();
				break;
			default:
				throw new NBTException("Tried to read tag as number: " + type);
			}
		}
		tagConsumed();
		return data;
	}

	@Override
	public String readStringTag() throws IOException {
		prepareTag();
		byte[] buffer = new byte[ioReadShort()];
		ioReadBytes(buffer);
		tagConsumed();
		return new String(buffer);
	}

	protected void prepareTag() throws IOException {
		prepareTag(false);
	}

	protected void prepareTag(boolean skip) throws IOException {
		ensureOpen();
		if (prepared)
			return;
		prepared = true;
		if (isList()) {
			type = list.type;
			if (isArrayTag())
				arrayTagPayload = ioReadInt();
			return;
		}
		int rawTag = ioReadByte();
		if (rawTag == -1)
			throw new IOException("End of stream reached!");
		type = TagType.getByID(rawTag);
		if (type == TagType.TAG_END) {
			return;
//			resetName();
//			depth--;
//			if (list != null && depth == list.depth)
//				type = TagType.LIST;
		} else if (!unnamedRoot || depth != -1) {
			if (!skip) {
				readName(name);
				hasName = true;
			} else {
				// skip name of tag
				skipBytes(ioReadShort());
			}
		}
		if (type == TagType.LIST)
			addList();
		else if (isArrayTag())
			arrayTagPayload = ioReadInt();
	}

	protected void resetName() {
		name.clear();
		hasName = false;
	}

	@Override
	public void readNextEntry() throws IOException {
		readNextEntry(false);
	}

	private void readNextEntry(boolean skipPrepare) throws IOException {
		prepareTag(skipPrepare);
		if (type == TagType.LIST) {
			boolean entered = list.entered;
			if (!list.entered) {
				depth++;
				list.entered = true;
			}
			if (list.type == TagType.COMPOUND) {
				depth++;
				type = TagType.COMPOUND;
				resetName();
				prepared = false;
				arrayTagPayload = -1;
				prepared = true;
				return;
			} else if (list.type == TagType.LIST) {
				depth++;
				addList();
				resetName();
				prepared = false;
				arrayTagPayload = -1;
				prepared = true;
				return;
			} else if (!entered) {
				resetName();
				prepared = false;
				arrayTagPayload = -1;
				return;
			}
		} else if (type == TagType.TAG_END) {
			depth--;
			tagConsumed();
			return;
		} else if (type == TagType.COMPOUND) {
			depth++;
			tagConsumed();
			return;
		}
		throw new IOException("Next entry should only be called on LIST, COMPOUND or END: " + type.name());
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
		list = new ListData(type, payload, depth + 1, list);
	}

	private void removeList() {
		if (list == null)
			return;
		list = list.parent;
		depth--;
		if (list != null && depth == list.depth)
			type = TagType.LIST;
	}

	@Override
	public void skipTag() throws IOException {
		skipTag(false);
	}

	protected void skipTag(boolean skipPrepare) throws IOException {
		prepareTag(skipPrepare);
		final boolean isList = list != null && depth == list.depth;
		switch (isList ? list.type : type) {
		case BYTE:
			skipTag(1, skipPrepare);
			break;
		case BYTE_ARRAY:
			skipTag(arrayTagPayload, skipPrepare);
			break;
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
		case DOUBLE:
			skipTag(8, skipPrepare);
			break;
		case FLOAT:
			skipTag(4, skipPrepare);
			break;
		case INT:
			skipTag(4, skipPrepare);
			break;
		case INT_ARRAY:
			skipTag(arrayTagPayload * 4, skipPrepare);
			break;
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
		case LONG:
			skipTag(8, skipPrepare);
			break;
		case LONG_ARRAY:
			skipTag(arrayTagPayload * 8, skipPrepare);
			break;
		case SHORT:
			skipTag(2, skipPrepare);
			break;
		case STRING:
			skipTag(ioReadShort(), skipPrepare);
			break;
		case TAG_END:
			readNextEntry(skipPrepare);
			break;
		}
	}

	@Override
	public void skipToEnd() throws IOException {
		final int depth = getDepth();
		while (depth <= getDepth()) {
			if (getType() == TagType.TAG_END && depth == getDepth())
				readNextEntry();
			else
				skipTag(true);
		}
	}

	private void skipTag(int bytes, boolean skipPrepare) throws IOException {
		skipBytes(bytes);
		tagConsumed();
	}

	@Override
	public boolean isList() {
		return list != null && depth == list.depth;
	}

	@Override
	public void mark() {
		markDepth = depth;
		if (list != null) {
			markList = list;
			list.mark();
		}
		markName = name.toString();
		markType = type;
		markArrayTagPayload = arrayTagPayload;
		markPrepared = prepared;
		ioMark();
	}

	@Override
	public void reset() {
		depth = markDepth;
		list = markList;
		markList = null;
		if (list != null)
			list.reset();
		name.clear();
		name.append(markName);
		markName = null;
		type = markType;
		arrayTagPayload = markArrayTagPayload;
		markArrayTagPayload = -1;
		prepared = markPrepared;
		ioReset();
	}

	@Override
	public int getArrayTagPayload() {
		return arrayTagPayload;
	}

	@Override
	public void close() {
		super.close();
		name = null;
		hasName = false;
		markName = null;
		depth = Integer.MIN_VALUE;
		markDepth = depth;
		list = null;
		markList = null;
	}

	/*
	 * --- Methods for reading data by subclass ---
	 */

	protected void readName(CharKeyBuffer buf) throws IOException {
		buf.clear();
		int utflen = ioReadShort() & 0xFFFF;
		byte[] byteBuff = nameBuf;
		if (byteBuff == null || byteBuff.length < utflen) {
			byteBuff = nameBuf = new byte[utflen];
		}
		int c, char2, char3;
		int count = 0;
		ioReadBytes(byteBuff, 0, utflen);
		while (count < utflen) {
			c = (int) byteBuff[count] & 0xff;
			if (c > 127)
				break;
			count++;
			buf.append((char) c);
		}
		while (count < utflen) {
			c = (int) byteBuff[count] & 0xff;
			switch (c >> 4) {
			case 0, 1, 2, 3, 4, 5, 6, 7 -> {
				/* 0xxxxxxx */
				count++;
				buf.append((char) c);
			}
			case 12, 13 -> {
				/* 110x xxxx 10xx xxxx */
				count += 2;
				if (count > utflen)
					throw new UTFDataFormatException("malformed input: partial character at end");
				char2 = byteBuff[count - 1];
				if ((char2 & 0xC0) != 0x80)
					throw new UTFDataFormatException("malformed input around byte " + count);
				buf.append((char) (((c & 0x1F) << 6) | (char2 & 0x3F)));
			}
			case 14 -> {
				/* 1110 xxxx 10xx xxxx 10xx xxxx */
				count += 3;
				if (count > utflen)
					throw new UTFDataFormatException("malformed input: partial character at end");
				char2 = byteBuff[count - 2];
				char3 = byteBuff[count - 1];
				if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
					throw new UTFDataFormatException("malformed input around byte " + (count - 1));
				buf.append((char) (((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0)));
			}
			default ->
				/* 10xx xxxx, 1111 xxxx */
				throw new UTFDataFormatException("malformed input around byte " + count);
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

	protected void ioReadBytes(byte[] buffer) throws IOException {
		ioReadBytes(buffer, 0, buffer.length);
	}
	
	protected abstract void ioReadBytes(byte[] buffer, int offset, int length) throws IOException;

	protected abstract short ioReadShort() throws IOException;

	protected abstract long ioReadLong() throws IOException;

	protected abstract float ioReadFloat() throws IOException;

	protected abstract double ioReadDouble() throws IOException;

	protected abstract void skipBytes(int bytes) throws IOException;

}
