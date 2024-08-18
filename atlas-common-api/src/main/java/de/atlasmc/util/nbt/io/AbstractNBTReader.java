package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;

public abstract class AbstractNBTReader implements NBTReader {
	
	protected boolean closed;
	
	@Override
	public void close() {
		this.closed = true;
	}
	
	protected void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("Stream closed!");
	}

	@Override
	public Number readNumber() throws IOException {
		Number data = null;
		TagType type = getType();
		switch (type) {
		case BYTE:
			data = readByteTag();
			break;
		case SHORT:
			data = readShortTag();
			break;
		case INT:
			data = readIntTag();
			break;
		case LONG:
			data = readLongTag();
			break;
		case FLOAT:
			data = readFloatTag();
			break;
		case DOUBLE:
			data = readDoubleTag();
			break;
		default:
			throw new NBTException("Tried to read tag as number: " + type);
		}
		return data;
	}
	
	@Override
	public boolean isArrayTag() {
		TagType type = null;
		try {
			type = getType();
		} catch (IOException e) {}
		return type == TagType.BYTE_ARRAY ||
				type == TagType.INT_ARRAY ||
				type == TagType.LONG_ARRAY;
	}
	
	@Override
	public NBT readNBT() throws IOException {
		final boolean isList = isList();
		String name = isList ? null : getFieldName().toString();
		switch (isList ? getListType() : getType()) {
		case BYTE: 
			return NBT.createByteTag(name, readByteTag());
		case BYTE_ARRAY: 
			return NBT.createByteArrayTag(name, readByteArrayTag());
		case COMPOUND: {
			if (isList) {
				final ListTag list = new ListTag(name, getListType());
				readNextEntry(); // move out of list header
				while (getRestPayload() > 0) {
					CompoundTag compound = new CompoundTag(name);
					final int depth = getDepth(); // root depth of compound
					while (depth <= getDepth()) {
						if (getType() == TagType.TAG_END) {
							readNextEntry(); // move out of list or to next compound in list
							continue;
						}
						compound.addTag(readNBT());
					}
					list.addTag(compound);
				}
				return list;
			}
			final CompoundTag compound = new CompoundTag(name);
			readNextEntry(); // move to first compound entry
			final int depth = getDepth(); // root depth of compound
			while (depth <= getDepth()) {
				if (getType() == TagType.TAG_END) {
					readNextEntry(); // skip end
					continue;
				}
				compound.addTag(readNBT());
			}
			return compound;
		}
		case DOUBLE: 
			return NBT.createDoubleTag(name, readDoubleTag());
		case FLOAT: 
			return NBT.createFloatTag(name, readFloatTag());
		case INT: 
			return NBT.createIntTag(name, readIntTag());
		case INT_ARRAY: 
			return NBT.createIntArrayTag(name, readIntArrayTag());
		case LIST: 
			if (isList) {
				ListTag list = new ListTag(name, TagType.LIST);
				readNextEntry();
				while (getRestPayload() > 0) {
					list.addTag(readNBT());
				}
				return list;
			}
			final ListTag list = new ListTag(name, getListType());
			readNextEntry();
			while (getRestPayload() > 0) {
				list.addTag(readNBT());
			}
			return list;
		case LONG: 
			return NBT.createLongTag(name, readLongTag());
		case LONG_ARRAY: 
			return NBT.createLongArrayTag(name, readLongArrayTag());
		case SHORT: 
			return NBT.createShortTag(name, readShortTag());
		case STRING: 
			return NBT.createStringTag(name, readStringTag());
		case TAG_END: 
			readNextEntry(); 
			return null;
		default:
			throw new NBTException("Error while reading NBT: isList=" + isList + " Type=" + getType() + " ListType=" + (isList ? getListType() : null));
		}
	}
	
	@Override
	public void search(CharSequence key, TagType stype, boolean slist) throws IOException {
		final int depth = getDepth();
		while (depth <= getDepth()) {
			TagType type = getType();
			// check if current tag is the result
			if ((key == null || getFieldName().equals(key)) && (stype == null || !slist ? stype == type : type == TagType.LIST && stype == getListType())) 
				break; // breaks if search result == true
			// --- Skip to next ---
			// Handle Compound, List(Compound) and List(List)
			if (type == TagType.COMPOUND || (type == TagType.LIST && (getListType() == TagType.COMPOUND || getListType() == TagType.LIST))) {
				readNextEntry(); // progress to first element of list or compound
			} else skipTag(); // progress to next
		}
	}
	
	@Override
	public UUID readUUID() throws IOException {
		int[] values = readIntArrayTag();
		if (values.length != 4) 
			throw new NBTException("Invalid UUID data length: " + values.length);
		long most = (values[0] << 32) | values[1];
		long least = (values[2] << 32) | values[3];
		return new UUID(most, least);
	}
	
	@Override
	public void skipToEnd() throws IOException {
		final int depth = getDepth();
		while (depth <= getDepth()) {
			if (getType() == TagType.TAG_END && depth == getDepth()) {
				readNextEntry();
			} else {
				skipTag();
			}
		}
	}

}