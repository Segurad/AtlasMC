package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.UUID;
import java.util.function.LongSupplier;

import de.atlasmc.util.nbt.TagType;

public abstract class AbstractNBTIOWriter extends AbstractNBTWriter {
	
	private int depth;
	private ListData list;
	private final boolean unnamedRoot;
	private byte[] nameBuf;
	
	public AbstractNBTIOWriter() {
		this(false);
	}
	
	public AbstractNBTIOWriter(boolean unnamedRoot) {
		depth = -1;
		this.unnamedRoot = unnamedRoot;
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
	
	private void prepareTag(TagType type, CharSequence name) throws IOException {
		ensureOpen();
		if (list != null && list.depth == depth) {
			if (list.payload > 0) {
				if (type != list.type && !(list.type == TagType.COMPOUND && type == TagType.TAG_END)) {
					throw new IOException("TagType not campatiple with ListTag(" + list.type.name() + "):" + type.name());
				}
				if (type == TagType.TAG_END) {
					ioWriteByte(type.getID());
					return;
				}
				list.payload--;
				if (list.payload == 0) {
					removeList();
				}
				return;
			} else 
				throw new IOException("Max Listpayload reached!");
		}
		ioWriteByte(type.getID());
		if (type == TagType.TAG_END)
			return;
		if (!unnamedRoot || depth != -1)
			writeUTF(name);
	}

	private void addList(int payload, TagType payloadType) {
		if (payload <= 0) 
			return;
		list = new ListData(payloadType, payload, ++depth, list);
	}
	
	private void removeList() {
		if (list == null) 
			return;
		list = list.parent;
		depth--;
	}
	
	@Override
	public void close() throws IOException {
		super.close();
		depth = Integer.MIN_VALUE;
		list = null;
		nameBuf = null;
	}
	
	/*
	 * --- Methods for writing data by subclass ---
	 */
	
	protected void writeUTF(CharSequence str) throws IOException {
		if (str == null) {
			ioWriteShort(0);
			return;
		}
		final int strlen = str.length();
        int utflen = strlen; // optimized for ASCII

        for (int i = 0; i < strlen; i++) {
            int c = str.charAt(i);
            if (c >= 0x80 || c == 0)
                utflen += (c >= 0x800) ? 2 : 1;
        }

        if (utflen > 65535 || /* overflow */ utflen < strlen)
            throw new UTFDataFormatException(tooLongMsg(str.toString(), utflen));
        final byte[] bytearr;
        if (nameBuf == null || nameBuf.length < utflen) {
        	bytearr = nameBuf = new byte[utflen];
        } else {
        	bytearr = nameBuf;
        }
        int count = 0;
        ioWriteShort(utflen);
        int i = 0;
        for (i = 0; i < strlen; i++) { // optimized for initial run of ASCII
            int c = str.charAt(i);
            if (c >= 0x80 || c == 0) break;
            bytearr[count++] = (byte) c;
        }

        for (; i < strlen; i++) {
            int c = str.charAt(i);
            if (c < 0x80 && c != 0) {
                bytearr[count++] = (byte) c;
            } else if (c >= 0x800) {
                bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                bytearr[count++] = (byte) (0x80 | ((c >>  6) & 0x3F));
                bytearr[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            } else {
                bytearr[count++] = (byte) (0xC0 | ((c >>  6) & 0x1F));
                bytearr[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            }
        }
        ioWriteBytes(bytearr, 0, utflen);
	}
	
    private static String tooLongMsg(String s, int bits32) {
        int slen = s.length();
        String head = s.substring(0, 8);
        String tail = s.substring(slen - 8, slen);
        // handle int overflow with max 3x expansion
        long actualLength = (long)slen + Integer.toUnsignedLong(bits32 - slen);
        return "encoded string (" + head + "..." + tail + ") too long: "
            + actualLength + " bytes";
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
