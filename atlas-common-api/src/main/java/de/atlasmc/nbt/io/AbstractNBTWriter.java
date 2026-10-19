package de.atlasmc.nbt.io;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.tag.NBT;

public abstract class AbstractNBTWriter implements NBTWriter {
	
	private boolean closed;
	
	@Override
	public void writeNBT(NBT nbt) throws IOException {
		ensureOpen();
		nbt.toNBT(this, true);
	}
	
	@Override
	public void writeNBT(CharSequence name, NBT nbt) throws IOException {
		ensureOpen();
		nbt.toNBT(name, this, true);
	}
	
	@Override
	public void writeNBT(CharSequence name, NBTReader reader) throws IOException {
		ensureOpen();
		final boolean isList = reader.isList();
		if (isList) {
			name = null;
		}
		switch (reader.getType()) {
		case BYTE: 
			writeByteTag(name, reader.readByteTag());
			return;
		case BYTE_ARRAY:
			writeByteArrayTag(name, reader.readByteArrayTag());
			return;
		case COMPOUND: {
			writeCompoundTag(name);
			reader.readNextEntry(); // move to first compound entry
			while (reader.getType() != TagType.TAG_END) {
				writeNBT(reader);
			}
			reader.readNextEntry(); // move out of compound
			writeEndTag();
			return;
		}
		case DOUBLE:
			writeDoubleTag(name, reader.readDoubleTag());
			return;
		case FLOAT: 
			writeFloatTag(name, reader.readFloatTag());
			return;
		case INT: 
			writeIntTag(name, reader.readIntTag());
			return;
		case INT_ARRAY:
			writeIntArrayTag(name, reader.readIntArrayTag());
			return;
		case LIST:
			writeListTag(name, reader.getListType(), reader.getNextPayload());
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				writeNBT(null, reader);
			}
			reader.readNextEntry();
			return;
		case LONG: 
			writeLongTag(name, reader.readLongTag());
			return;
		case LONG_ARRAY:
			writeLongArrayTag(name, reader.readLongArrayTag());
			return;
		case SHORT:
			writeShortTag(name, reader.readShortTag());
			return;
		case STRING:
			writeStringTag(name, reader.readStringTag());
			return;
		case TAG_END: 
			writeEndTag(); 
			return;
		default:
			throw new NBTException("Error while reading NBT: isList=" + isList + " Type=" + reader.getType() + " ListType=" + (isList ? reader.getListType() : null));
		}
	}
	
	@Override
	public void writeUUID(CharSequence name, UUID uuid) throws IOException {
		ensureOpen();
		writeIntArrayTag(name, new int[] {
				(int) (uuid.getMostSignificantBits()>>32),
				(int) uuid.getMostSignificantBits(),
				(int) (uuid.getLeastSignificantBits()>>32),
				(int) uuid.getLeastSignificantBits()
		});
	}
	
	@Override
	public void close() throws IOException {
		this.closed = true;
	}
	
	protected final void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("Stream closed!");
	}

	/**
	 * Returns the number of bytes required to encode the given string as modified UTF-8 as used by NBT
	 * @param str
	 * @return number of bytes
	 */
	protected static long getUTFLength(CharSequence str) {
		final int strlen = str.length();
		long utflen = strlen;
		for (int i = 0; i < strlen; i++) {
			int c = str.charAt(i);
			if (c >= 0x80 || c == 0)
				utflen += (c >= 0x800) ? 2 : 1;
		}
		return utflen;
	}
	
	/**
	 * Ensures that the given string can be stored as NBT string
	 * @param value
	 * @throws IllegalArgumentException if the encoded value is longer than 65535 bytes
	 */
	protected static void ensureStringLength(CharSequence value) {
		final long length = getUTFLength(value);
		if (length > 0xFFFF)
			throw new IllegalArgumentException("Encoded string longer than max length: " + length + " > " + 0xFFFF);
	}

}
